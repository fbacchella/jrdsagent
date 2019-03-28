package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IfStat extends LProbeProc {

    private static final Path NETROUTEFILE = Paths.get("/proc/net/route");
    private static final String DEFAULTROUTE = "00000000";

    private String ifName;

    /**
     * Will resolve to the interface that carries the default route
     * @see jrds.agent.linux.LProbeProc#configure()
     *
     * @param ifName
     * @param autoroute
     * @return
     */
    public Boolean configure(String ifName, Boolean autoroute) {
        if (! autoroute) {
            return configure(ifName);
        } else {
            try {
                List<String> lines = Files.readAllLines(NETROUTEFILE, StandardCharsets.US_ASCII);
                // Removing first line as it's the header
                lines.remove(0);
                for (String line : lines) {
                    if (!line.equals("")) {
                        String[] lineSplit = line.trim().split("\\s+");
                        if (lineSplit.length < 2) {
                            continue;
                        }
                        String itf = lineSplit[0];
                        String destination = lineSplit[1];
                        if (DEFAULTROUTE.equals(destination)) {
                            this.ifName = itf;
                            break;
                        }
                    }
                }
                return super.configure();
            } catch (Exception e) {
                throw new RuntimeException("Impossible to initialize IfStat probe", e);
            }
        }
    }

    public Boolean configure(String ifName) {
        this.ifName = ifName;
        return super.configure();
    }

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<>();
        String line;

        //Jump other first 2 lines
        r.readLine();
        r.readLine();

        while((line = r.readLine()) != null) {
            if(line.indexOf(" " + ifName + ":") >= 0) {
                String[] ifvalues = line.trim().split(":");
                String[] values = ifvalues[1].trim().split("\\s+");
                retValues.put("rxbits", 8 * Double.parseDouble(values[0]));
                retValues.put("rxpackets", Double.valueOf(values[1]));
                retValues.put("rxerrs", Double.valueOf(values[2]));
                retValues.put("rxdrop", Double.valueOf(values[3]));
                retValues.put("rxfifo", Double.valueOf(values[4]));
                retValues.put("rxframe", Double.valueOf(values[5]));
                retValues.put("rxcompressed", Double.valueOf(values[6]));
                retValues.put("rxmulticast", Double.valueOf(values[7]));
                retValues.put("txbits", 8 * Double.parseDouble(values[8]));
                retValues.put("txpackets", Double.valueOf(values[9]));
                retValues.put("txerrs", Double.valueOf(values[10]));
                retValues.put("txdrop", Double.valueOf(values[11]));
                retValues.put("txfifo", Double.valueOf(values[12]));
                retValues.put("txcolls", Double.valueOf(values[13]));
                retValues.put("txcarrier", Double.valueOf(values[14]));
                retValues.put("txcompressed", Double.valueOf(values[15]));
            }
        }
        return retValues;
    }

    public String getName() {
        return "ifstat:" + ifName;
    }

}
