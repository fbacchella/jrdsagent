package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.LProbe;

/**
 * Probe for polling standard Linux sensors utility for CPU's core temperatures. 
 *
 * @author drone
 */
public class Sensors extends LProbe {
    String SENSORS_COMMAND = "/usr/bin/sensors";

    Pattern sensorValuePattern = Pattern.compile("(\\+|-)?\\d+(\\.\\d+)?");
    
    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<String, Number>();
        try {
            queryFile(SENSORS_COMMAND, retValues);
        } catch (Exception e) {
            throw new RuntimeException(this.getName(), e);
        }
        return retValues;
    }

    public void queryFile(String command, Map<String, Number> retValues) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while((line = r.readLine()) != null) {
            
            String[] split = line.split(":");
            
            if (split.length < 2) {
                continue;
            }
            
            Matcher valueMatcher = sensorValuePattern.matcher(split[1]);
            if (!valueMatcher.find()) {
                continue;
            }
//            System.out.println(split[0] + ": " + Double.valueOf(valueMatcher.group()));
            try {
                retValues.put(split[0], Double.valueOf(valueMatcher.group()));
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    public String getName() {
        return "sensors";
    }
//    public static void main(String args[]) {
//        new Sensors().query();
//    }
}
