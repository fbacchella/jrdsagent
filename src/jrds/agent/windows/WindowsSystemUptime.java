package jrds.agent.windows;

import java.util.Date;
import java.util.concurrent.ExecutionException;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
import jrds.agent.SystemUptime;

public class WindowsSystemUptime extends SystemUptime {

    private static final String UPTIMERELPATH= "Win32_PerfFormattedData_PerfOS_System";
    private static final String UPTIMEFIELD = "SystemUpTime";

    @Override
    protected Date systemStartTime() {
        try {
            String formatedString = (String) WmiRequester.getFromClass(UPTIMERELPATH, UPTIMEFIELD).get(0);
            long formated = Long.parseLong(formatedString);
            return new Date(new Date().getTime() - formated * 1000);
        } catch (InterruptedException  e) {
            throw new RuntimeException("failed to get start time", e);
        } catch (ExecutionException  e) {
            throw new RuntimeException("failed to get start time", e);
        }
    }

}
