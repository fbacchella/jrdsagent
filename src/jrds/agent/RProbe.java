package jrds.agent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RProbe extends Remote {
	static String NAME = "dispatcher";
	
	public Map query(String name) throws RemoteException;
	public String prepare(String name, List args) throws RemoteException;
	public boolean exist(String name)  throws RemoteException;
	public long getUptime() throws RemoteException;
}
