package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Snmp extends LProbeProc {
    Pattern separator = Pattern.compile(":? +");

    @Override
    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;
        String[] headers = null;
        while((line = r.readLine()) != null) {
            String[] content = separator.split(line);
            if (headers == null) {
                headers = content;
            } else {
                String protocol = headers[0];
                for (int i=1; i < headers.length; i++) {
                    try {
                        Number val = Integer.parseInt(content[i]);
                        String name = String.format("%s.%s", protocol, headers[i]);
                        retValues.put(name, val);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                }
                headers = null;
            }
        }
        return retValues;
    }
}
