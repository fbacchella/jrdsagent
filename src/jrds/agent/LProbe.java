package jrds.agent;

import java.rmi.RemoteException;
import java.util.Map;

public interface LProbe {
	public Map query()  throws RemoteException;
	
	/**
	 * Used to generate an uniq name for the local instance of the probe
	 * can be meaningless (not intend to human use), but must be
	 * persistent accross reboot
	 * @return the probe instance name
	 */
	public String getName()  throws RemoteException;
}
