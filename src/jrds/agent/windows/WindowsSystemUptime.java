package jrds.agent.windows;

import jrds.agent.Start;
import jrds.agent.SystemUptime;

public class WindowsSystemUptime extends SystemUptime {

    private static final String UPTIMERELPATH= "Win32_PerfFormattedData_PerfOS_System";
    private static final String UPTIMEFIELD = "SystemUpTime";

    public WindowsSystemUptime() {
        WmiRequester.getItem(UPTIMERELPATH);
    }

    @Override
    public long getSystemUptime() {
        WmiRequester.refresh();
        String formatedString = (String) WmiRequester.getFromClass(UPTIMERELPATH, UPTIMEFIELD)[0];
        long formated = Start.parseStringNumber(formatedString, Long.class, 0l).longValue();
        return formated * 1000;
    }

}
