package jrds.probe;

import java.util.Map;

import jrds.factories.ProbeBean;

@ProbeBean({"index", "pattern"})
public class ProcessInfo extends RMIIndexed {
    private String processName;

    public Boolean configure(String indexName, String pattern) {
        this.processName = indexName;
        return configure(pattern);
    }

    public String getIndexName() {
        return processName;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMI#getNewSampleValues()
     */
    @Override
    public Map<String, Number> getNewSampleValues() {
        Map<String, Number> retValues = super.getNewSampleValues();
        if(retValues != null && retValues.containsKey("uptime")) {
            long uptime = retValues.get("uptime").longValue();
            setUptime(uptime);
            retValues.remove("uptime");
        }
        else
            setUptime(0);
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

}
