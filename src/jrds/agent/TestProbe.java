package jrds.agent;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class TestProbe implements LProbe {

	public String getName() throws RemoteException {
		return "TestProbe";
	}

	public Map query() throws RemoteException {
		Map m = new HashMap(1);
		m.put("time", new Double(System.currentTimeMillis()));
		return m;
	}

}
