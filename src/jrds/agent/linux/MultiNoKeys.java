package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.LProbe;

public class MultiNoKeys extends LProbe {

    private int keyIndex = 0;
    private String separator = "\\s+";

    public MultiNoKeys() {
        super();
    }

    public Boolean configure(Integer keyIndex, String separator) {
        if(keyIndex != null) {
            this.keyIndex = keyIndex.intValue();            
        } else {
            this.keyIndex = 0;
        }
        if(separator != null && ! separator.isEmpty()) {
            this.separator = separator;            
        } else {
            this.separator = "\\s+";
        }
        return Boolean.TRUE;
    }

    public String getName() {
        return getStatFile().getPath().replace(getStatFile().getParent(), "");
    }

    public Map<String, Number> query() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(getStatFile()));
            return parse(r);
        } catch (Exception e) {
            throw new RuntimeException(getName(), e);
        }
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<String, Number>();

        String line;
        while((line = r.readLine()) != null) {
            String[] values = line.trim().split(separator);
            if(values.length < keyIndex + 1) {
                continue;
            }
            String key = values[keyIndex];
            for(int i=0; i < values.length; i++) {
                try {
                    Number parsed = new Double(values[i]);
                    String localKey = key + "." + i;
                    retValues.put(localKey, parsed);
                } catch (NumberFormatException e) {
                }
            }
        }
        return retValues;
    }

}
