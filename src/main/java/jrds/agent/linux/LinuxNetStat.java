package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import jrds.agent.CollectException;
import jrds.agent.LProbe;

public class LinuxNetStat extends LProbe {

    private static final Path NETSTAT_FILE = Path.of("/proc/net/netstat");
    private static final Path SNMP_FILE = Path.of("/proc/net/snmp");
    private static final Path SOCKET_STATFILE = Path.of("/proc/net/sockstat");

    Pattern separator = Pattern.compile(":? +");

    private String protocol;

    @Override
    public void setProperty(String specific, String value) {
        if ("protocol".equals(specific)) {
            protocol = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public Boolean configure() {
        return Files.isReadable(NETSTAT_FILE) && Files.isReadable(SNMP_FILE) && Files.isReadable(SOCKET_STATFILE);
    }

    @Override
    public Map<String, Number> query() {
        Map<String, Number> retValues = new HashMap<>();
        parseWithHeader(protocol, SNMP_FILE, retValues);
        parseWithHeader(protocol + "Ext", NETSTAT_FILE, retValues);
        parseSockStat(protocol.toUpperCase(Locale.ROOT), retValues);
        return retValues;
    }

    private void parseSockStat(String protocolKey, Map<String, Number> retValues) {
        try (BufferedReader r = Files.newBufferedReader(SOCKET_STATFILE, StandardCharsets.US_ASCII)) {
            String line;
            while((line = r.readLine()) != null) {
                String[] content = separator.split(line);
                if (protocolKey.equalsIgnoreCase(content[0])) {
                    for (int i = 1 ; i < content.length; i+=2) {
                        String key = content[i];
                        try {
                            Number value = Long.valueOf(content[i + 1]);
                            retValues.put(key, value);
                        } catch (NumberFormatException e) {
                            // Skipping invalid values
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new CollectException("Unable to read /proc/net/sockstat for " + getName(), e);
        }
    }

    private void parseWithHeader(String protocol, Path statFile, Map<String, Number> retValues) {
        try (BufferedReader r = Files.newBufferedReader(statFile, StandardCharsets.US_ASCII)) {
            String line;
            String[] headers = null;
            while((line = r.readLine()) != null) {
                String[] content = separator.split(line);
                if (headers == null && protocol.equalsIgnoreCase(content[0])) {
                    headers = content;
                } else if (headers != null) {
                    for (int i = 1; i < headers.length; i++) {
                        try {
                            Number val = Long.valueOf(content[i]);
                            retValues.put(headers[i], val);
                        } catch (NumberFormatException e) {
                            // Skipping invalid values
                        }
                    }
                    headers = null;
                }
            }
        } catch (IOException e) {
            throw new CollectException("Unable to read /proc/net/sockstat for " + getName(), e);
        }
    }

    /**
     * Used to generate an uniq name for the local instance of the probe
     * can be meaningless (not intend to human use), but must be
     * persistent accross reboot
     *
     * @return the probe instance name
     */
    @Override
    public String getName() {
        return "LinuxNetStat" + protocol;
    }

}
