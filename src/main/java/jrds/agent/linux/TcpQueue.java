package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TcpQueue extends LProbeProc {
    int port;
    boolean local;
    static final int offset = 2;

    public Boolean configure(Integer port, Boolean local) {
        this.port = port.intValue();
        this.local = local.booleanValue();
        return true;
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;
        int portCol = 2;
        if(local)
            portCol = 1;
        //Jump other first line
        r.readLine();

        int rx = 0;
        int tx = 0;
        int num = 0;
        while((line = r.readLine()) != null) {
            String[] values = line.trim().split("\\s+");
            String portString = values[portCol].split(":")[1];
            int foundPort = Integer.parseInt(portString, 16);
            if(foundPort == port) {
                String[] queues = values[4].split(":");
                tx += Integer.parseInt(queues[0], 16);
                rx += Integer.parseInt(queues[1], 16);
                num++;
            }
        }
        if(num != 0) {
            retValues.put("tx", (double)tx/num);
            retValues.put("rx", (double)rx/num);
        }
        return retValues;
    }

    public String getName() {
        return "tcpq:" + port;
    }

}
