package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import jrds.agent.Start;

public class MultiNoKeys extends LProbeProc {

    //The column where the "line name" is extracted from
    private int keyIndex = 0;
    private Pattern separatorPattern = Pattern.compile("\\s+"); 

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
            separatorPattern = Pattern.compile(separator);
        }
        return super.configure();
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<String, Number>();

        String line;
        while ((line = r.readLine()) != null) {
            String[] values = separatorPattern.split(line.trim());
            //Skip line if it's too short
            if (values.length < keyIndex + 1) {
                continue;
            }
            String keyPrefix = (keyIndex >= 0 ? values[keyIndex] : "") + ".";
            for (int i=0; i < values.length; i++) {
                if (i == keyIndex) {
                    continue;
                }
                Number parsed = Start.parseStringNumber(values[i], Double.NaN);
                String localKey = keyPrefix + i;
                retValues.put(localKey, parsed);
            }
        }
        return retValues;
    }

}
