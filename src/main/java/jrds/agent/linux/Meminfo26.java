package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.Start;

public class Meminfo26 extends LProbeProc {
    private static final Pattern linePattern = Pattern.compile("(\\w+):\\s*(\\d+).*");

    @Override
    public String getName() {
        return "meminfo";
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        //This value is not always present, put a sensible default
        retValues.put("Hugepagesize", 2048 * 1024);
        String line;
        while((line = r.readLine()) != null) {
            Matcher m = linePattern.matcher(line);
            if(m.matches()) {
                String key = m.group(1);
                long value = Start.parseStringNumber(m.group(2), 0L);
                value *= 1024;
                retValues.put(key, value);
            }
        }

        //Conversion of huge page from number to bytes
        Number hugepagesize = retValues.remove("Hugepagesize");
        if(hugepagesize != null) {
            long pg_size = hugepagesize.longValue();
            for(String hugePage_key: new String[] {"HugePages_Total", "HugePages_Free", "HugePages_Rsvd", "HugePages_Surp"}) {
                Number value = retValues.get(hugePage_key);
                //If the kernel is compiled without huge page
                if(value != null) {
                    long hugePage_value = value.longValue();
                    hugePage_value *= pg_size;
                    retValues.put(hugePage_key, hugePage_value);
                }
            }
        }
        return retValues;
    }

}
