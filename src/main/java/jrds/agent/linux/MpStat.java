package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.Start;

public class MpStat extends LProbeProc {

    private Pattern cpuPattern;
    private String[] columns;

    @Override
    public void setProperty(String specific, String value) {
        if ("columns".equals(specific)) {
            columns = value.split(" *, *");
            String groupsPattern = "";
            for (int i = columns.length - 1; i >= 0 ; i--) {
                groupsPattern = String.format("(\\s+(?<%s>\\d+)%s)?", columns[i], groupsPattern);
            }
            cpuPattern = Pattern.compile("cpu(?<cpu>\\d+)" + groupsPattern + ".*");
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public String getName() {
        return "mpstat";
    }

    @Override
    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;
        while((line = r.readLine()) != null) {
            if (line.startsWith("cpu ")) {
                continue;
            }
            if (! line.startsWith("cpu")) {
                continue;
            }
            Matcher m = cpuPattern.matcher(line);
            if (m.matches()) {
                for (String g: columns) {
                    String value = m.group(g);
                    retValues.put(g + m.group("cpu"), Start.parseStringNumber(value, Double.NaN));
                }
            }
        }
        return retValues;
    }

}
