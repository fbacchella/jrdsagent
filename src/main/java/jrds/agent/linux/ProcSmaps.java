package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.CollectException;

public class ProcSmaps extends AbstractProcessParser {

    private static final Pattern LINEPATTERN;
    static {
        String headerPattern = "[0-9a-f]+-[0-9a-f]+ (?<perm>....) [0-9a-f]+ (?<majorminor>[0-9a-f]+:[0-9a-f]+) \\d+ *(?<filename>.+)?";
        String sizePattern = "(?<key>.*): +(?<value>\\d+) kB";
        LINEPATTERN = Pattern.compile(String.format("^(?:%s)|(?:%s)$", headerPattern, sizePattern));
    }
    private static final Set<String> IGNORE = Set.of("KernelPageSize", "MMUPageSize");

    @Override
    protected Map<String, Number> parseProc(Path pidDir) {
        long startParse = System.nanoTime();
        Matcher match = LINEPATTERN.matcher("");
        Path smaps = pidDir.resolve("smaps");
        Map<String, Map<String, Long>> areadetails = new HashMap<>();
        try (BufferedReader r = newAsciiReader(smaps)){
            String line;
            Map<String, Long> currentareaddetails = null;
            while ((line = r.readLine()) != null) {
                match.reset(line);
                if ( !match.matches()) {
                    continue;
                }
                String majorminor = match.group("majorminor");
                String key = match.group("key");
                if (majorminor != null) {
                    String areaname = majorminor;
                    String filename = match.group("filename");
                    if ("00:00".equals(majorminor) || filename.startsWith("/memfd")) {
                        areaname = "anonymous";
                    } else if (filename != null) {
                        areaname = "mappedfiles";
                    }
                    if (! areadetails.containsKey(areaname)) {
                        areadetails.put(areaname, new HashMap<>(14));
                    }
                    currentareaddetails = areadetails.get(areaname);
                 } else if (currentareaddetails != null && key != null && ! IGNORE.contains(key)) {
                    long value = Long.parseLong(match.group("value")) * 1024;
                    currentareaddetails.compute(key, (k, v) -> v == null ? value : v + value);
                }
            }
        } catch (FileNotFoundException e) {
            return Collections.emptyMap();
        } catch (IOException e) {
            throw new CollectException("Collect for " + getName() + " failed: " + e.getMessage(), e);
        }
        long endParse = System.nanoTime();
        Map<String, Number> collected = new HashMap<>();
        for(Map.Entry<String, Map<String, Long>> i: areadetails.entrySet()) {
            for (Map.Entry<String, Long> j: i.getValue().entrySet()) {
                collected.put(String.format("%s:%s", i.getKey(), j.getKey()), j.getValue());
            }
        }
        collected.put("parsingTime", 1e-9 * (endParse - startParse));
        return collected;
    }

    @Override
    protected long getProcUptime(Map<String, Number> values) {
        return Long.MAX_VALUE;
    }

    @Override
    public String getName() {
        return "pismaps-" + getNameSuffix();
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
