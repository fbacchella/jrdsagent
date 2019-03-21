package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.Start;

public class MultiNoKeys extends LProbeProc {

    //The column where the "line name" is extracted from
    private int keyIndex = 0;
    private String separator = "\\s+";

    public Boolean configure(Long keyIndex, String separator) {
        return doconfigure(keyIndex, separator);
    }

    public Boolean configure(Integer keyIndex, String separator) {
        return doconfigure(keyIndex, separator);
    }

    // Needed because jolokia deserializaion can resolve to a Long the keyIndex
    private boolean doconfigure(Number keyIndex, String separator) {
        if (keyIndex != null) {
            this.keyIndex = keyIndex.intValue();
        }
        if (separator != null && ! separator.isEmpty()) {
            this.separator = separator;
        }
        return super.configure();
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<String, Number>();

        String line;
        while ((line = r.readLine()) != null) {
            String[] values = line.trim().split(separator);
            //Skip line if it's too short
            if (values.length < keyIndex + 1) {
                continue;
            }
            String keyPrefix = values[keyIndex] + ".";
            for (int i=0; i < values.length; i++) {
                Number parsed = Start.parseStringNumber(values[i], Double.NaN);
                String localKey = keyPrefix + i;
                retValues.put(localKey, parsed);
            }
        }
        return retValues;
    }

}
