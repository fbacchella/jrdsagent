package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.CollectException;
import jrds.agent.LProbe;
import jrds.agent.Start;

public class TcpStat extends LProbe {

    private static final String STATFILE = "/proc/net/netstat";
    private static final String SNMPFILE = "/proc/net/snmp";

    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<>();
        queryFile(SNMPFILE, "Tcp:", retValues);
        queryFile(STATFILE, "TcpExt:", retValues);
        return retValues;
    }

    public void queryFile(String file, String prefix, Map<String, Number> retValues) {
        try (BufferedReader r = newAsciiReader(file)) {
            String line;

            while((line = r.readLine()) != null) {
                String[] keys  = line.trim().split("\\s+");
                line = r.readLine();
                String[] values = line.trim().split("\\s+");
                if( ! keys[0].trim().equals(prefix))
                    continue;

                for(int i=0; i < keys.length; i++) {
                    Double value = Start.parseStringNumber(values[i], Double.NaN);
                    retValues.put(keys[i], value);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CollectException("File not found  " + file + " for " + getName());
        } catch (IOException e) {
            throw new CollectException("Unable to read " + file + " for " + getName() + ": " + e.getMessage(), e);
        }
    }

    public String getName() {
        return "netstat";
    }

}
