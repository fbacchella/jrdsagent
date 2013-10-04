package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.LProbe;

public class TcpStat extends LProbe {
    private final static String STATFILE = "/proc/net/netstat";
    private final static String SNMPFILE = "/proc/net/snmp";

    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<String, Number>();
        queryFile(SNMPFILE, "Tcp:", retValues);
        queryFile(STATFILE, "TcpExt:", retValues);
        return retValues;
    }

    public void queryFile(String file, String prefix, Map<String, Number> retValues) {
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(file));
            String line;

            while((line = r.readLine()) != null) {
                String[] keys  = line.trim().split("\\s+");
                line = r.readLine();
                String[] values = line.trim().split("\\s+");
                if( ! keys[0].trim().equals(prefix))
                    continue;

                for(int i=0; i < keys.length; i++) {
                    try {
                        retValues.put(keys[i], new Double(values[i]));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            r.close();
        } catch (Exception e) {
            if(r != null) {
                try {
                    r.close();
                } catch (IOException e1) {
                    throw new RuntimeException(getName(), e1);
                }
            }
            throw new RuntimeException(getName(), e);
        }
    }

    public String getName() {
        return "netstat";
    }

}
