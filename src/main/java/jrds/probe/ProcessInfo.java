package jrds.probe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import jrds.factories.ProbeBean;

@ProbeBean({"index", "pattern", "self"})
public class ProcessInfo extends RMIIndexed {
    private String processName;
    private boolean self = false;

    @Override
    public Boolean configure() {
        if (!super.configure()) {
            return false;
        }
        if (! self && super.getIndex() == null) {
            log(Level.ERROR, "No proccess indentification given");
            return false;
        }
        List<Object> args = new ArrayList<>(1);
        if (! self) {
            args.add(super.getIndex());
        } else {
            args.add(Boolean.TRUE);
        }
        setArgs(args);
        return true;
    }

    public Boolean configure(String indexName, String pattern) {
        this.processName = indexName;
        return configure(pattern);
    }

    @Override
    public String getIndexName() {
        return processName;
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
        return super.getIndex();
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        super.setIndex(pattern);
        self = false;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIIndexed#getIndex()
     */
    @Override
    public String getIndex() {
        return processName;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIIndexed#setIndex(java.lang.String)
     */
    @Override
    public void setIndex(String index) {
        this.processName = index;
    }

    public String isSelf() {
        return Boolean.toString(self);
    }

    public void setSelf(String self) {
        this.self = Boolean.parseBoolean(self) || self.isEmpty();
        if (this.self) {
            super.setIndex(null);
        }
    }

}
