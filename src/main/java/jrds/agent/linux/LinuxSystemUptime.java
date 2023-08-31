package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import jrds.agent.CollectException;
import jrds.agent.Start;
import jrds.agent.SystemUptime;

public class LinuxSystemUptime extends SystemUptime {

    private static final Path UPTIMEFILE = Path.of("/proc/uptime");

    @Override
    public long getSystemUptime() {
        try (BufferedReader r = Files.newBufferedReader(UPTIMEFILE, StandardCharsets.US_ASCII)) {
            String[] uptimes = r.readLine().trim().split(" ");
            return (long)( Start.parseStringNumber(uptimes[0], 0.0) * 1000);
        } catch (FileNotFoundException e) {
            throw new CollectException("/proc/uptime not found", e);
        } catch (IOException e) {
            throw new CollectException("Cant' read /proc/uptime: " + e.getMessage(), e);
        }
    }
}
