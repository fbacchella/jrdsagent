package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import jrds.agent.LProbe;

public class TcpStat implements LProbe {
	String STATFILE = "/proc/net/netstat";
	String SNMPFILE = "/proc/net/snmp";


	public Map query() throws RemoteException {
		Map retValues = new HashMap();
		try {
			queryFile(SNMPFILE, "Tcp:", retValues);
			queryFile(STATFILE, "TcpExt:", retValues);
		} catch (Exception e) {
			throw new RemoteException(this.getName(), e);
		}
		return retValues;
	}

	public void queryFile(String file, String prefix, Map retValues) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(file));
		String line;

		while((line = r.readLine()) != null) {
			String[] keys  = line.trim().split("\\s+");
			line = r.readLine();
			String[] values = line.trim().split("\\s+");
			if( ! keys[0].trim().equals(prefix))
				continue;

			for(int i=0; i < keys.length; i++) {
				try {
					retValues.put(keys[i], new Double(values[i]));
				} catch (NumberFormatException e) {
				}
			}
		}
	}

	public String getName() throws RemoteException {
		return "netstat";
	}

}
