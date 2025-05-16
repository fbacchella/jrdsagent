package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jrds.agent.CollectException;

public class ProcSmaps extends AbstractProcessParser {

    private static final Pattern LINEPATTERN;
    static {
        String headerPattern = "[0-9a-f]+-[0-9a-f]+ (?<perm>....) [0-9a-f]+ (?<majorminor>[0-9a-f]+:[0-9a-f]+) \\d+ *(?<filename>.+)?";
        String sizePattern = "(?<key>.*): +(?<value>\\d+) kB";
        LINEPATTERN = Pattern.compile(String.format("^(?:%s)|(?:%s)$", headerPattern, sizePattern));
    }
    private static final Set<String> IGNORE = Set.of("KernelPageSize", "MMUPageSize");

    private String smapsFile = "smaps";

    @Override
    public void setProperty(String specific, String value) {
        if ("smapsFile".equals(specific) && value != null) {
            smapsFile = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    protected Map<String, Number> parseProc(Path pidDir) {
        Matcher match = LINEPATTERN.matcher("");
        Path smaps = pidDir.resolve(smapsFile);
        Map<String, AtomicLong> mappingRss = new HashMap<>();
        Map<String, AtomicLong> mappingPss = new HashMap<>();
        Map<String, Map<String, Long>> areadetails = new HashMap<>();
        List<String> lines;
        // Fast file read, to release any lock
        long startRead = System.nanoTime();
        try (BufferedReader r = newAsciiReader(smaps)) {
            lines = r.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            return Collections.emptyMap();
        } catch (IOException e) {
            throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
        }
        long endRead = System.nanoTime();
        Map<String, Long> currentareaddetails = null;
        boolean memfd = false;
        for (String line: lines) {
            match.reset(line);
            if ( !match.matches()) {
                continue;
            }
            String majorminor = match.group("majorminor");
            String key = match.group("key");
            String filename = match.group("filename");
            // It's a memfd, so anonymous memory, but Anonymous field value is always 0
            if (majorminor != null) {
                memfd = "00:01".equals(majorminor);
                String areaname;
                if ("00:00".equals(majorminor) || "00:01".equals(majorminor)) {
                    areaname = "anonymous";
                } else if (filename != null) {
                    areaname = "mappedfiles";
                } else {
                    areaname = filename;
                }
                currentareaddetails = areadetails.computeIfAbsent(areaname, k -> new HashMap<>(14));
            } else if (currentareaddetails != null && key != null && ! IGNORE.contains(key)) {
                long value = Long.parseLong(match.group("value")) * 1024;
                currentareaddetails.compute(key, (k, v) -> v == null ? value : v + value);
                if ("Rss".equals(key) && filename != null) {
                    mappingRss.computeIfAbsent(filename, k -> new AtomicLong()).addAndGet(value);
                } else if ("Pss".equals(key) && filename != null) {
                    mappingPss.computeIfAbsent(filename, k -> new AtomicLong()).addAndGet(value);
                } else if("Rss".equals(key) && memfd) {
                    // It's a memfd, so anonymous memory, but Anonymous entry value is always 0.
                    currentareaddetails.compute("Anonymous", (k, v) -> v == null ? value : v + value);
                }
            }
        }
        Map<String, Number> collected = new HashMap<>();
        for (Map.Entry<String, Map<String, Long>> i: areadetails.entrySet()) {
            for (Map.Entry<String, Long> j: i.getValue().entrySet()) {
                collected.put(String.format("%s:%s", i.getKey(), j.getKey()), j.getValue());
            }
        }
        collected.put("parsingTime", 1e-9 * (endRead - startRead));
        return collected;
    }

    @Override
    protected long getProcUptime(Map<String, Number> values) {
        return Long.MAX_VALUE;
    }

    @Override
    public String getName() {
        return String.format("pismaps/%s-%s", smapsFile, getNameSuffix());
    }

    /**
     * Don't care about uptime, it collects only gauge
     * @see jrds.agent.linux.AbstractProcessParser#computeUpTime(long)
     */
    @Override
    protected long computeUpTime(long startTime) {
        return Long.MAX_VALUE;
    }

}
