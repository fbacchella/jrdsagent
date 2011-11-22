package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jrds.agent.RProbeActor;

public class RProbeActorLinux extends RProbeActor {
    final private static String UPTIMEFILE = "/proc/uptime";
    final static private long startime = System.currentTimeMillis();

    public long getUptime() {
        File uptimef = new File(UPTIMEFILE);
        //Put a default value, being the agent uptime
        long uptime = (System.currentTimeMillis() - startime)/1000;
        if(uptimef.canRead()) {
            try {
                BufferedReader r = new BufferedReader(new FileReader(uptimef));
                String uptimes[] = r.readLine().trim().split(" ");
                uptime = (long) Double.parseDouble(uptimes[0]);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (NumberFormatException e) {
            }

        }
        return uptime;
    }

}
