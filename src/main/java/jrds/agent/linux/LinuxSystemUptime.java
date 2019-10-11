package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jrds.agent.CollectException;
import jrds.agent.Start;
import jrds.agent.SystemUptime;

public class LinuxSystemUptime extends SystemUptime {
    static private final String UPTIMEFILE = "/proc/uptime";

    @Override
    public long getSystemUptime() {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(UPTIMEFILE), StandardCharsets.US_ASCII))) {
            String uptimes[] = r.readLine().trim().split(" ");
            return (long)( Start.parseStringNumber(uptimes[0], 0.0) * 1000);
        } catch (FileNotFoundException e) {
            throw new CollectException("/proc/uptime not found", e);
        } catch (IOException e) {
            throw new CollectException("Cant' read /proc/uptime: " + e.getMessage(), e);
        }
    }
}
