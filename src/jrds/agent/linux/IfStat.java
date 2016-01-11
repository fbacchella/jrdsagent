package jrds.agent.linux;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.File;

public class IfStat extends LProbeProc {
    private static final String netDev = "/proc/net/route";
    String ifName;

    public Boolean configure() {
        List<String> lines;
        try {
            lines = FileUtils.readLines(new File(netDev));
            // Removing first line as it's the header
            lines.remove(0);
            for (String line : lines) {
                if (!line.equals("")) {
                    String[] lineSplit = line.trim().split("\\s+");
                    String itf = lineSplit[0];
                    String firstVal = lineSplit[1];
                    if (Integer.parseInt(firstVal, 16) == 0) {
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

    public Map<String, Number> parse(BufferedReader r) throws IOException {
        Map<String, Number> retValues = new HashMap<String, Number>();
        String line;

        //Jump other first 2 lines
        r.readLine();
        r.readLine();

        while((line = r.readLine()) != null) {
            if(line.indexOf(" " + ifName + ":") >= 0) {
                String[] ifvalues = line.trim().split(":");
                String[] values = ifvalues[1].trim().split("\\s+");
                retValues.put("rxbits", new Double( 8 * Double.parseDouble(values[0])));
                retValues.put("rxpackets", Double.valueOf(values[1]));
                retValues.put("rxerrs", Double.valueOf(values[2]));
                retValues.put("rxdrop", Double.valueOf(values[3]));
                retValues.put("rxfifo", Double.valueOf(values[4]));
                retValues.put("rxframe", Double.valueOf(values[5]));
                retValues.put("rxcompressed", Double.valueOf(values[6]));
                retValues.put("rxmulticast", Double.valueOf(values[7]));
                retValues.put("txbits", new Double( 8 * Double.parseDouble(values[8])));
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
