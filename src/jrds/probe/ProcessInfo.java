package jrds.probe;

import java.util.Map;

public class ProcessInfo extends RMIIndexed {
	private String indexName;
	private long uptime = 0;

	public void configure(String indexName, String pattern) {
		super.configure(pattern);
		this.indexName = indexName;
	}
	public String getIndexName() {
		return indexName;
	}
	/* (non-Javadoc)
	 * @see jrds.probe.RMI#getNewSampleValues()
	 */
	@Override
	public Map getNewSampleValues() {
		Map retValues = super.getNewSampleValues();
		uptime = ((Number)retValues.get("uptime")).longValue();
		
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
