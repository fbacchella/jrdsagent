package jrds.agent.solaris;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jrds.agent.LProbe;
import uk.co.petertribble.jkstat.JKstat;

public class KStat  implements LProbe {
	Map kstatEntries;
	private final int DATANOEXIST = -2;
	private final int KSTATNOEXIST = -2;
	private final int KSTAT_DATA_CHAR = 0;
	private final int KSTAT_DATA_INT32 = 1;
	private final int KSTAT_DATA_UINT32 = 2;
	private final int KSTAT_DATA_INT64 = 3;
	private final int KSTAT_DATA_UINT64 = 4;
	
	
	
	public KStat(Map kstatEntries) {
		this.kstatEntries = new HashMap(kstatEntries.size());
		this.kstatEntries.putAll(kstatEntries);
		this.kstatEntries.put("Uptime", "unix:0:system_misc:boot_time");
		
	}
	
	public Map query() {
		JKstat jkstat = new JKstat();
		Map retValues = new HashMap(kstatEntries.size());
		for(Iterator i = retValues.entrySet().iterator() ; i.hasNext() ;) {
			Map.Entry e= (Map.Entry) i.next();
			String dsName = (String) e.getKey();
			String kstat = (String) e.getValue();
			String[] kstats = kstat.split(":");

			String module = kstats[0];
		    String instanceStr = kstats[1];
		    String kname = kstats[2];
		    String statistic = kstats[3];

		    int instance = Integer.parseInt(instanceStr);

		    
			double d = 0;
			int type = jkstat.getKstatDataType(module, instance, kname, statistic);
			switch(type) {
			case KSTAT_DATA_INT32:
				d = (double) jkstat.lookupInt(module, instance, kname, statistic);
				break;
			case KSTAT_DATA_UINT32:
				d = (double) jkstat.lookupUInt(module, instance, kname, statistic);
				break;
			case KSTAT_DATA_INT64:
				d = (double) jkstat.lookupLong(module, instance, kname, statistic);
				break;
			case KSTAT_DATA_UINT64:
				d = (double) jkstat.lookupULong(module, instance, kname, statistic);
				break;
			}
			retValues.put(dsName, new Double(d)); 
		}
		return retValues;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
