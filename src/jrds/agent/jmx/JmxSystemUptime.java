package jrds.agent.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

import jrds.agent.SystemUptime;

public class JmxSystemUptime extends SystemUptime {

    @Override
    protected Date systemStartTime() {
        RuntimeMXBean rtbean = ManagementFactory.getRuntimeMXBean();

        return new Date(rtbean.getStartTime());
    }

}
