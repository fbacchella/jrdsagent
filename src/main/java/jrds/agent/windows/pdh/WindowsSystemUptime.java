package jrds.agent.windows.pdh;

import java.util.Date;

import com.arkanosis.jpdh.Counter;
import com.arkanosis.jpdh.JPDH;
import com.arkanosis.jpdh.JPDHException;
import com.arkanosis.jpdh.Query;

import jrds.agent.CollectException;
import jrds.agent.SystemUptime;

public class WindowsSystemUptime extends SystemUptime {

    /* *
     * It calculate the Windows start time. So PDH is called only once at the initialization.
     * @see jrds.agent.SystemUptime#systemStartTime()
     */
    @Override
    protected Date systemStartTime() {
        try (Query query = JPDH.openQuery()) {
            Counter uptime = null;
            try {
                uptime = query.addCounter("\\System\\System Up Time");
                query.collectData();
                return new Date(new Date().getTime() - uptime.getLongValue() * 1000L);
            } finally {
                if (uptime != null) {
                    query.removeCounter(uptime);
                }
            }
        } catch (JPDHException e) {
            throw new CollectException("Unable to get the OS uptime: " + e.getMessage(), e);
        }
    }

}
