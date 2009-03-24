package jrds.agent;

import java.io.FileDescriptor;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Start implements Serializable {
	static int port = 2002;

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		String portProp = System.getProperty("jrds.port");
		if(portProp != null) {
			try {
				int tryPort = Integer.parseInt(portProp);
				if(tryPort !=0)
					port = tryPort;
			} catch (NumberFormatException e) {
			}
		}

		if (System.getSecurityManager() == null)
			System.setSecurityManager ( new RMISecurityManager() {
				public void checkAccept(String host, int port) {}
				public void checkAccess(Thread t) {}
				public void checkAccess(ThreadGroup g) {}
				public void checkConnect(String host, int port, Object context) {}
				public void checkConnect(String host, int port) {}
				public void checkRead(FileDescriptor fd) {}
				public void checkRead(String file, Object context) {}
				public void checkRead(String file) {}
			});
		RProbe dispatcher = new RProbeImpl(port + 1);

		Registry registry = LocateRegistry.createRegistry(port);
		registry.bind(RProbe.NAME, dispatcher);
		//Make it wait on himself to wait forever
		try {
			Thread.currentThread().join();
			System.out.print("joined");
		} catch (InterruptedException e) {
		}
	}
}
