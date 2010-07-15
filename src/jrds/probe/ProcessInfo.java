package jrds.probe;

import java.util.Map;

public class ProcessInfo extends RMIIndexed {
	private String indexName;
	private long uptime = 0;

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
		uptime = retValues.get("uptime").longValue();
		
		retValues.remove("uptime");
		return retValues;
	}
	
	/* (non-Javadoc)
	 * @see jrds.probe.RMI#getUptime()
	 */
	@Override
	public long getUptime() {
		return uptime;
	}
	
}
