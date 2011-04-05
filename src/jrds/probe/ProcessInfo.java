package jrds.probe;

import java.util.Map;

public class ProcessInfo extends RMIIndexed {
    private String indexName;

    public Boolean configure(String indexName, String pattern) {
        this.indexName = indexName;
        return configure(pattern);
    }

    public String getIndexName() {
        return indexName;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMI#getNewSampleValues()
     */
    @Override
    public Map<String, Number> getNewSampleValues() {
        Map<String, Number> retValues = super.getNewSampleValues();
        if(retValues.containsKey("uptime")) {
            long uptime = retValues.get("uptime").longValue();
            setUptime(uptime);
            retValues.remove("uptime");
        }
        else
            setUptime(0);
        return retValues;
    }

}
