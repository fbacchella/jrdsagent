package jrds.probe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.event.Level;

import jrds.factories.ProbeBean;

@ProbeBean({"index", "pattern", "exeName"})
public class ProcessInfo extends RMIIndexed {
    private String pattern;
    private String exeName;
    private boolean self = false;

    @Override
    public Boolean configure() {
        if (!super.configure()) {
            return false;
        }
        if (! self && getIndex() == null) {
            log(Level.ERROR, "No process identification given");
            return false;
        }
        List<Object> args = new ArrayList<>(1);
        if (! self) {
            args.add(getIndex());
        } else {
            args.add(Boolean.TRUE);
        }
        setArgs(args);
        return true;
    }

    public Boolean configure(String index, String pattern) {
        setIndex(index);
        this.pattern = pattern;
        return configure();
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMI#getNewSampleValues()
     */
    @Override
    public Map<String, Number> getNewSampleValues() {
        Map<String, Number> retValues = super.getNewSampleValues();
        if (retValues != null && retValues.containsKey("uptime")) {
            long uptime = retValues.get("uptime").longValue();
            setUptime(uptime);
            retValues.remove("uptime");
        } else {
            setUptime(0);
        }
        return retValues;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
        self = false;
    }

    public String isSelf() {
        return Boolean.toString(self);
    }

    public void setSelf(String self) {
        this.self = Boolean.parseBoolean(self) || self.isEmpty();
        if (this.self) {
            super.setIndex(null);
            pattern = null;
        }
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
    }

}
