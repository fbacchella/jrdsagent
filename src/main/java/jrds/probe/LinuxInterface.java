package jrds.probe;

import java.util.ArrayList;
import java.util.List;

import jrds.Util;
import jrds.factories.ProbeBean;

@ProbeBean({"ifName", "defaultroute"})
public class LinuxInterface extends RMI implements IndexedProbe {

    private String ifName = null;
    private boolean defaultroute = false;

    @Override
    public Boolean configure() {
        if (!super.configure()) {
            return false;
        }
        List<Object> args = new ArrayList<>(1);
        args.add(Util.parseTemplate(ifName, this));
        // the arg autoroute is used only if true
        // this allows to use old jrdsagent
        if (defaultroute) {
            args.add(defaultroute);
        }
        setArgs(args);
        return true;
    }

    /**
     * Kept for compatibly with old probes
     * @param ifName
     * @return
     */
    public Boolean configure(String ifName) {
        this.ifName = ifName;
        this.defaultroute = false;
        return configure();
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return ifName;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.ifName = index;
        defaultroute = false;
    }

    /**
     * @return the index
     */
    public String getIfName() {
        return ifName;
    }

    /**
     * @param index the index to set
     */
    public void setIfName(String index) {
        this.ifName = index;
        defaultroute = false;
    }

    /**
     * @return the autoroute
     */
    public String getDefaultroute() {
        return Boolean.toString(defaultroute);
    }

    /**
     * @param autoroute the autoroute to set
     */
    public void setDefaultroute(String autoroute) {
        this.defaultroute = Boolean.parseBoolean(autoroute);
        if (this.defaultroute) {
            ifName = "default";
        }
    }

    @Override
    public String getIndexName() {
        return ifName;
    }
}
