package jrds.agent;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Map;

public abstract class LProbe {
	private File statFile;

	public LProbe() {
		super();
	}

	public abstract Map<String, Number> query()  throws RemoteException;
	
	/**
	 * Used to generate an uniq name for the local instance of the probe
	 * can be meaningless (not intend to human use), but must be
	 * persistent accross reboot
	 * @return the probe instance name
	 */
	public abstract String getName()  throws RemoteException;
	
	/**
	 * @return the statFile
	 */
	protected File getStatFile() {
		return statFile;
	}

	/**
	 * @param statFile the statFile to set
	 */
	public void setStatFile(File statFile) {
		this.statFile = statFile;
	}

}
