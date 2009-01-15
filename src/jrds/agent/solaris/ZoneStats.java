package jrds.agent.solaris;

import java.rmi.RemoteException;
import java.util.Map;

import jrds.agent.LProbe;

public class ZoneStats implements LProbe {
	String zone;
	
	public ZoneStats(String zone) {
		this.zone = zone;
	}

	public String getName() throws RemoteException {
		return "zonestat:" + zone;
	}

	public Map query() throws RemoteException {
		return null;
	}


}
