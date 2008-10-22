package jrds.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RProbeImpl extends UnicastRemoteObject implements RProbe, Serializable {
	static final long serialVersionUID = -7914792289084645089L;

	final private Map probeMap = new HashMap();
	final private long startime = System.currentTimeMillis();
	final private String UPTIMEFILE = "/proc/uptime";

	public RProbeImpl() throws RemoteException {
		super();
	}

	public Map query(String name) throws RemoteException {
		Map retValue = new HashMap(0);
		LProbe p = (LProbe) probeMap.get(name);
		if(p != null)
			retValue = p.query();
		else
			System.err.println(name + " not found");
		return retValue;
	}

	public String prepare(String name, List args) throws RemoteException {
		String iname = null;
		LProbe p = null;
		try {
			Class probeClass = Class.forName(name);
			Class[] argsType = new Class[args.size()];
			Object[] argsVal = new Object[args.size()];
			int index = 0;
			for(Iterator i = args.iterator(); i.hasNext() ; index++) {
				Object thisarg = i.next();
				argsType[index] = thisarg.getClass();
				argsVal[index] = thisarg;
			}
			Constructor theConst = probeClass.getConstructor(argsType);
			if(theConst != null) {
				p = (LProbe) theConst.newInstance(argsVal);
				iname = p.getName();
				probeMap.put(iname, p);
			}
		} catch (Exception e) {
			throw new RemoteException("name", e);
		}
		return iname;
	}

	public boolean exist(String name) throws RemoteException {
		return probeMap.containsKey(name);
	}

	public long getUptime() throws RemoteException {
		File uptimef = new File(UPTIMEFILE);
		long uptime = (System.currentTimeMillis() - startime)/1000;
		if(uptimef.canRead()) {
			try {
				BufferedReader r = new BufferedReader(new FileReader(uptimef));
				String uptimes[] = r.readLine().trim().split(" ");
				uptime = (long) Double.parseDouble(uptimes[0]);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} catch (NumberFormatException e) {
			}

		}
		return uptime;
	}
}
