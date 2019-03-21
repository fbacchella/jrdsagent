package jrds.agent;

import java.util.Date;

public abstract class SystemUptime {

    // store the system start time, in the same specifications than date
    private final long systemStartTime;

    public SystemUptime() {
        systemStartTime = systemStartTime().getTime();
    }

    public long getSystemUptime() {
        return System.currentTimeMillis() - systemStartTime;
    }

    /**
     * @return the system start time in Date specification
     */
    protected Date systemStartTime() {
        return new Date();
    }

}
