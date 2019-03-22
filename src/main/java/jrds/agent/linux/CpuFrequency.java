package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.Start;

public class CpuFrequency extends LProbeProc {

    private static final Pattern LINE = Pattern.compile("^(core id|physical id|cpu MHz)\\s+:\\s+([\\d+\\.]+)");

    private short numcpu = 32;

    @Override
    public Map<String, Number> parse(BufferedReader r) throws IOException {
        String line;
        String core = "";
        String physical = "";
        int rank=0;
        Map<String, Number> values = new HashMap<>(numcpu);
        Set<String> seen = new HashSet<>(numcpu);
        while((line = r.readLine()) != null) {
            Matcher m = LINE.matcher(line);
            if (m.matches()) {
                switch(m.group(1)) {
                case "core id":
                    core = m.group(2);
                    break;
                case "physical id":
                    physical = m.group(2);
                    break;
                case "cpu MHz":
                    String identity = core + "." + physical;
                    if (! seen.contains(identity)) {
                        float frequency = Start.parseStringNumber(m.group(2), 0f);
                        frequency *= 1e6f;
                        values.put(Integer.toString(rank++), frequency);
                        seen.add(identity);
                    }
                }
            }
        }
        numcpu = (short) values.size();
        return values;
    }

}
