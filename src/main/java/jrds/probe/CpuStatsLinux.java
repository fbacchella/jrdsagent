package jrds.probe;

import jrds.GraphDesc;
import jrds.factories.ProbeBean;

@ProbeBean({"guest", "virtual"})
public class CpuStatsLinux extends MultiNoKeys {

    private boolean guest = false;
    private boolean virtual = false;

    @Override
    public void addGraph(GraphDesc gd) {
        if ("CPUTimeStatLinuxBase".equals(gd.getName()) && ! guest && ! virtual) {
            super.addGraph(gd);
        } else if ("CPUTimeStatLinuxHost".equals(gd.getName()) && guest && ! virtual) {
            super.addGraph(gd);
        } else if ("CPUTimeStatLinuxVM".equals(gd.getName()) && ! guest && virtual) {
            super.addGraph(gd);
        }
    }

    /**
     * @return the guest
     */
    public boolean isGuest() {
        return guest;
    }
    /**
     * @param guest the guest to set
     */
    public void setGuest(boolean guest) {
        this.guest = guest;
    }
    /**
     * @return the virtual
     */
    public boolean isVirtual() {
        return virtual;
    }
    /**
     * @param virtual the virtual to set
     */
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

}
