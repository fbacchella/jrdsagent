package jrds.agent;

import java.util.Map;

public abstract class LProbe {

    public Boolean configure() {
        return true;
    }

    public abstract Map<String, Number> query();

    /**
     * Used to generate an uniq name for the local instance of the probe
     * can be meaningless (not intend to human use), but must be
     * persistent accross reboot
     * @return the probe instance name
     */
    public abstract String getName();

    /**
     * Some property that might be configured for this prope
     * Each implementation should overide it and catch intersting property
     * but dont forger to call super(...) if not intersted in it
     * @param specific
     * @param value
     */
    public void setProperty(String specific, String value) {
    }

}
