package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jrds.agent.Start;
import jrds.agent.SystemUptime;

public class LinuxSystemUptime extends SystemUptime {
    final static private String UPTIMEFILE = "/proc/uptime";

    @Override
    public long getSystemUptime() {
        File uptimef = new File(UPTIMEFILE);
        try (BufferedReader r = new BufferedReader(new FileReader(uptimef))){
            String uptimes[] = r.readLine().trim().split(" ");
            long uptime = (long)( Start.parseStringNumber(uptimes[0], 0.0) * 1000);
            return uptime;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("/proc/uptime not found", e);
        } catch (IOException e) {
            throw new RuntimeException("cant' read /proc/uptime", e);
        }
    }
}
