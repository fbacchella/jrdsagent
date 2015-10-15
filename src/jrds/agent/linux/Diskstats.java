package jrds.agent.linux;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Diskstats extends LProbeProc {
    String disk;
    static private final int offset = 2;

    public Boolean configure(String disk) {
        this.disk = disk;
        return true;
    }

    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<String, Number>();
        try {
            BufferedReader r = readStatFile();
            String line;
            boolean found = false;
            while(! found && ((line = r.readLine()) != null)) {
                if(line.indexOf(" " + disk + " ") >= 0) {
                    found = true;
                    String[] values = line.trim().split("\\s+");
                    //Full line, with all the values
                    if(values.length > offset + 7 ) {
                        retValues.put("rrqm", Double.valueOf(values[offset + 2]));
                        retValues.put("wrqm", Double.valueOf(values[offset + 6]));
                        retValues.put("r", Double.valueOf(values[offset + 1]));
                        retValues.put("w", Double.valueOf(values[offset + 5]));
                        retValues.put("rsec", Double.valueOf(values[offset + 3]));
                        retValues.put("wsec", Double.valueOf(values[offset + 7]));
                        retValues.put("rwait", Double.valueOf(values[offset + 4]));
                        retValues.put("wwait", Double.valueOf(values[offset + 8]));
                        retValues.put("qu-sz", Double.valueOf(values[offset + 9]));
                        retValues.put("waittm", Double.valueOf(values[offset + 10]));
                        retValues.put("wwaittm", Double.valueOf(values[offset + 11]));
                    }
                    else {
                        retValues.put("r", Double.valueOf(values[offset + 1]));
                        retValues.put("rsec", Double.valueOf(values[offset + 2]));
                        retValues.put("w", Double.valueOf(values[offset + 3]));
                        retValues.put("wsec", Double.valueOf(values[offset + 4]));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(this.getName(), e);
        }
        return retValues;
    }

    public String getName() {
        return "ds-" + disk;
    }

}
