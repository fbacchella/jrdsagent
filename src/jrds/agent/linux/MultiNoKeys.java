package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultiNoKeys extends LProbeProc {

    //The column where the "line name" is extracted from
    private int keyIndex = 0;
    private String separator = "\\s+";

    public Boolean configure(Integer keyIndex, String separator) {
        if(keyIndex != null) {
            this.keyIndex = keyIndex;            
        }
        if(separator != null && ! separator.isEmpty()) {
            this.separator = separator;            
        }
        return super.configure();
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
            String keyPrefix = values[keyIndex] + ".";
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
