package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.LProbe;
import jrds.agent.Start;

public class Meminfo26 implements LProbe {
	private static final String STATFILE = "/proc/meminfo";
	private static final Pattern linePattern = Pattern.compile("(\\w+):\\s*(\\d+).*");

	public String getName() throws RemoteException {
		return "meminfo";
	}

	public Map<String, Number> query() throws RemoteException {
		Map<String, Number> retValues = new HashMap<String, Number>();
		//This value is not always present, put a sensible default
		retValues.put("Hugepagesize", 2048 * 1024);
		try {
			BufferedReader r = new BufferedReader(new FileReader(STATFILE));
			String line;
			while((line = r.readLine()) != null) {
				Matcher m = linePattern.matcher(line);
				if(m.matches()) {
					String key = m.group(1);
					long value = Start.parseStringNumber(m.group(2), Long.class, 0).longValue();
					value *= 1024;
					retValues.put(key, value);
				}
			}

			//Conversion of huge page from number to bytes
			Number hugepagesize = retValues.remove("Hugepagesize");
			if(hugepagesize != null) {
				long pg_size = hugepagesize.longValue();
				for(String hugePage_key: new String[] {"HugePages_Total", "HugePages_Free", "HugePages_Rsvd", "HugePages_Surp"}) {
					Number value = retValues.get(hugePage_key);
					//If the kernel is compiled without huge page
					if(value != null) {
						long hugePage_value = value.longValue();
						hugePage_value *= pg_size;
						retValues.put(hugePage_key, hugePage_value);
					}
				}
			}
		} catch (Exception e) {
			throw new RemoteException(this.getName(), e);
		}
		return retValues;
	}

}
