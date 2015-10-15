package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultiNoKeys extends LProbeProc {

    private int keyIndex = -1;
    private String separator = "\\s+";

    public MultiNoKeys() {
        super();
    }

    public Boolean configure(Integer keyIndex, String separator) {
        if(keyIndex != null) {
            this.keyIndex = keyIndex.intValue();            
        }
        if(separator != null && ! separator.isEmpty()) {
            this.separator = separator;            
        } else {
            this.separator = "\\s+";
        }
        return Boolean.TRUE;
    }

    public String getName() {
        File statFile = getStatFile();
        return statFile.getPath().replace(statFile.getParent(), "");
    }

    public Map<String, Number> query() {
        try {
            BufferedReader r = readStatFile();
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
            //Skip line if it's too short
            if(values.length < keyIndex + 1) {
                continue;
            }
            //Prefix value with column key value
            //but only if keyIndex is given a legal value
            String keyPrefix = "";
            if(keyIndex >= 0) {
                keyPrefix = values[keyIndex] + ".";                
            }
            for(int i=0; i < values.length; i++) {
                try {
                    Number parsed = new Double(values[i]);
                    String localKey = keyPrefix + i;
                    retValues.put(localKey, parsed);
                } catch (NumberFormatException e) {
                }
            }
        }
        return retValues;
    }

}
