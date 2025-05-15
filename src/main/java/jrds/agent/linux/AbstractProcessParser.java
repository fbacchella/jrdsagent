package jrds.agent.linux;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import jrds.agent.CollectException;
import jrds.agent.LProbe;

public abstract class AbstractProcessParser extends LProbe {

    protected static final Path PROC_PATH = Paths.get("/proc");
    protected static final Pattern SPACE_SEPARATOR = Pattern.compile("\\s+");
    protected static final Pattern COLON_SEPARATOR = Pattern.compile(":");

    private static final int AROBE = '@';

    private static final int USER_HZ = 100;

    private static class QueryValues {
        int count = 0;
        long mostRecentTick = 0;
        Map<String, Number> retValues = new HashMap<>();

        @Override
        public String toString() {
            return "QueryValues{" + "count=" + count + ", mostRecentTick=" + mostRecentTick + ", retValues=" + retValues + '}';
        }
    }

    private class Matchers {
        final Matcher cmdMatcher;
        final Matcher exeMatcher;
        final Matcher kernelMatcher;
        Matchers(Pattern kernelThreadFilter, Pattern exeFilter, Pattern cmdFilter) {
            cmdMatcher = cmdFilter != null ? cmdFilter.matcher("") : null;
            exeMatcher = exeFilter != null ? exeFilter.matcher("") : null;
            kernelMatcher = kernelThreadFilter != null ? kernelThreadFilter.matcher("") : null;

        }
        boolean isKernelMatcher() {
            return kernelMatcher != null && cmdMatcher == null && exeMatcher == null;
        }
        boolean isProcessMatcher() {
            return kernelMatcher == null && (cmdMatcher != null || exeMatcher != null);
        }

        private synchronized boolean matchKernelThread(Path pidDir) {
            try {
                CharSequence comm = readLine(pidDir.resolve("comm"));
                return kernelMatcher.reset(comm).matches();
            } catch (IOException e) {
                return false;
            } finally {
                // Matcher.reset() don't drop the previous searched text
                kernelMatcher.reset("");
            }
        }

        private synchronized boolean matchRegularProcess(Path pidDir) {
            if (exeMatcher != null) {
                try {
                    Path exePath = pidDir.resolve("exe").toAbsolutePath().toRealPath();
                    if (! exeMatcher.reset(exePath.toString()).matches()) {
                        return false;
                    }
                } catch (IOException e) {
                    // Some exe link are broken and can't be read
                    return false;
                } finally {
                    // Matcher.reset() don't drop the previous searched text
                    exeMatcher.reset("");
                }
            }
            if (cmdMatcher != null && Files.isRegularFile(pidDir.resolve("cmdline"))) {
                try {
                    CharSequence cmdLine = getCmdLine(pidDir);
                    if (cmdLine != null && cmdLine.length() > 0 && cmdLine.charAt(cmdLine.length() - 1) == ' ') {
                        cmdLine = cmdLine.subSequence(0, cmdLine.length() -1);
                    }
                    if (! cmdMatcher.reset(cmdLine).matches()) {
                        return false;
                    }
                } finally {
                    // Matcher.reset() don't drop the previous searched text
                    cmdMatcher.reset("");
                }
            }
            return true;
        }
    }

    private Pattern cmdFilter = null;
    private Pattern exeFilter = null;
    private Pattern kernelThreadFilter = null;
    private String index = null;
    private Matchers matchers = null;

    public Boolean configure(String index) {
        this.index = index;
        // Backward compatibility where index is also cmdFilter
        if (cmdFilter == null && exeFilter == null && kernelThreadFilter == null) {
            cmdFilter = Pattern.compile(index);
        }
        matchers = new Matchers(kernelThreadFilter, exeFilter, cmdFilter);
        return matchers.isProcessMatcher() || matchers.isKernelMatcher();
    }

    /**
     * Called when self is set to true
     * @param self always true
     * @return
     */
    public Boolean configure(Boolean self) {
        this.cmdFilter = null;
        return true;
    }

    @Override
    public void setProperty(String specific, String value) {
        if ("exeName".equals(specific) && value != null) {
            exeFilter = Pattern.compile(value);
        } else if ("pattern".equals(specific) && value != null) {
            this.cmdFilter = Pattern.compile(value);
        } else if ("kernelThreadName".equals(specific) && value != null) {
            this.kernelThreadFilter = Pattern.compile(value);
        } else if ("index".equals(specific) && value != null) {
            this.index = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    public String getNameSuffix() {
        return Optional.ofNullable(index)
                       .or(() -> Optional.ofNullable(cmdFilter).map(c -> cmdFilter.pattern()))
                       .orElse("self");
    }

    public Map<String, Number> query() {
        QueryValues values = new QueryValues();
        processPids(p -> processQueryData(p, values));
        values.retValues.put("uptime", computeUpTime(values.mostRecentTick));
        values.retValues.put("count", values.count);
        return values.retValues;
    }

    void processQueryData(Path pidDir, QueryValues values) {
        boolean matched = false;
        boolean isRegularProcess = Files.isRegularFile(pidDir.resolve("exe")) && Files.isRegularFile(pidDir.resolve("cmdline"));
        if (isRegularProcess  && matchers.isProcessMatcher()) {
            matched = matchers.matchRegularProcess(pidDir);
        } else if (matchers.isKernelMatcher()){
            matched = matchers.matchKernelThread(pidDir);
        }
        if (matched) {
            Map<String, Number> bufferMap = parseProc(pidDir);
            long startTimeTick = getProcUptime(bufferMap);
            if (startTimeTick >= 0) {
                values.mostRecentTick = Math.max(startTimeTick, values.mostRecentTick);
                values.count++;
                for (Map.Entry<String, Number> e: bufferMap.entrySet()) {
                    values.retValues.compute(e.getKey(), (k, v) -> v == null ? e.getValue().doubleValue() : v.doubleValue() + e.getValue().doubleValue());
                }
            }
        }
    }

    private void processPids(Consumer<Path> process) {
        if (matchers == null) {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            int separator = jvmName.indexOf(AROBE);
            if (separator >= 0) {
                process.accept(PROC_PATH.resolve(jvmName.substring(0, separator)));
            }
        } else if (Files.isDirectory(PROC_PATH)) {
            Matcher pidDirMatcher = Pattern.compile("^\\d+$").matcher("");
            try (Stream<Path> procFiles = Files.list(PROC_PATH)) {
                procFiles.filter(p -> isProcDir(p, pidDirMatcher))
                         .forEach(process);
            } catch (IOException e) {
                throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
            }
        }
    }

    private boolean isProcDir(Path p, Matcher pidDirMatcher) {
        try {
            if (! Files.isDirectory(p)) {
                return false;
            }
            try {
                if (! pidDirMatcher.reset(p.getFileName().toString()).matches()) {
                    return false;
                }
            } finally {
                // Matcher.reset() don'd drop the previous searched text
                pidDirMatcher.reset("");
            }
            boolean isRegularProcess = Files.isRegularFile(p.resolve("exe")) && Files.isRegularFile(p.resolve("cmdline"));
            boolean isKernelThread = Files.isRegularFile(p.resolve("comm"));
            if (isRegularProcess && matchers.isProcessMatcher()) {
                return true;
            } else if (! isRegularProcess && isKernelThread && matchers.isKernelMatcher()){
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected CharSequence getCmdLine(Path pidDir) {
        Path cmdFile = pidDir.resolve("cmdline");
        try (InputStream is = Files.newInputStream(cmdFile)){
            byte[] content = new byte[4096];
            int read;
            StringBuilder buffer = new StringBuilder();
            while ((read = is.read(content)) > 0) {
                for (int i = 0 ; i < read ; i++) {
                    if (content[i] == 0) {
                        content[i] = ' ';
                    }
                }
                buffer.append(new String(content, 0, read, StandardCharsets.US_ASCII));
            }
            // The last character is an extra null byte
            // Some command don't have a cmdline
            return buffer.substring(0, Math.max(buffer.length() - 1, 0));
        } catch (FileNotFoundException e) {
            // A file not found is not a problem just return null
            return null;
        } catch (IOException e) {
            throw new CollectException("Unable to read " + cmdFile + " for " + getName(), e);
        }
    }

    protected abstract Map<String, Number> parseProc(Path pidDir);

    protected abstract long getProcUptime(Map<String, Number> bufferMap);

    /**
     * Compute the uptime, given the relative start time of a process
     * @param startTime in tick
     * @return uptime, in second
     */
    protected long computeUpTime(long startTime) {
        if (startTime == 0) {
            return 0;
        }

        try {
            CharSequence uptimeLine = readLine((PROC_PATH.resolve("uptime")));
            // We talk minutes here, so a precision down to second is good enough
            long uptimeSystem = (long) Double.parseDouble(SPACE_SEPARATOR.split(uptimeLine)[0]);
            return uptimeSystem - startTime / USER_HZ;
        } catch (IOException e) {
            throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
        }
    }

}
