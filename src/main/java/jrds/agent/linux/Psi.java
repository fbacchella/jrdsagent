package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Psi extends LProbeProc {

    private static final Pattern LINE_PATTERN = Pattern.compile("(?<key>[a-z]+)(?: avg[0-9]+=[.0-9]+)+ total=(?<total>[0-9]+)");

    private String source;

    @Override
    public void setProperty(String specific, String value) {
        if ("source".equals(specific)) {
            source = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> values = new HashMap<>(2);
        String line;
        while ((line = r.readLine()) != null) {
            Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                values.put(m.group("key"), Long.parseLong(m.group("total")));
            }
        }
        return values;
    }

    @Override
    public String getName() {
        return "psi-" + source;
    }
}
