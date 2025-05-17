package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import jrds.agent.CollectException;
import jrds.agent.Start;
import jrds.agent.SystemUptime;

public class LinuxSystemUptime extends SystemUptime {

    protected static final Pattern SPACE_SPLIT = Pattern.compile("\\s+");
    private static final Path UPTIMEFILE = Path.of("/proc/uptime");

    @Override
    public long getSystemUptime() {
        try (BufferedReader r = Files.newBufferedReader(UPTIMEFILE, StandardCharsets.US_ASCII)) {
            String[] uptimes = SPACE_SPLIT.split(r.readLine().trim());
            return Start.parseStringNumber(uptimes[0], 0.0).longValue() * 1000;
        } catch (FileNotFoundException e) {
            throw new CollectException("/proc/uptime not found", e);
        } catch (IOException e) {
            throw new CollectException("Cant' read /proc/uptime: " + e.getMessage(), e);
        }
    }
}
