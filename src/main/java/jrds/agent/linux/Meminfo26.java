package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meminfo26 extends LProbeProc {

    private static final Pattern linePattern = Pattern.compile("(\\w+)(?:\\((\\w+)\\))?:\\s*(\\d+)( kB)?.*");
    private static final List<String> HUGE_VALUE_KEYS = List.of("HugePages_Total", "HugePages_Free", "HugePages_Rsvd", "HugePages_Surp");

    @Override
    public String getName() {
        return "meminfo";
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;
        Matcher m = linePattern.matcher("");
        while ((line = r.readLine()) != null) {
            m.reset(line);
            if (m.matches()) {
                String key = m.group(1);
                if (m.group(2) != null) {
                    key += "_" +  m.group(2);
                }
                long value = Long.parseLong(m.group(3));
                // If suffixed by kB, transform to bytes
                if (m.group(4) != null) {
                    value *= 1024;
                }
                retValues.put(key, value);
            }
        }
        // This value is not always present, use a sensible default
        long pgSize = Optional.ofNullable(retValues.remove("Hugepagesize")).map(Number::longValue).orElse(2048L * 1024);
        // Convert huge pages numbers to bytes
        for (String hugePage_key: HUGE_VALUE_KEYS) {
            retValues.computeIfPresent(hugePage_key, (k, v) -> v.longValue() * pgSize);
        }
        return retValues;
    }

}
