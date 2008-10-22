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
	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		
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
		RProbe dispatcher = new RProbeImpl();
		
		Registry registry = LocateRegistry.createRegistry(2002);
		registry.bind(RProbe.NAME, dispatcher);
		Thread myThread = Thread.currentThread();
		//Awfull pseudo-daemon code
		while(true) {
			Thread[] allThreads = new Thread[Thread.activeCount() * 2 ];
			myThread.getThreadGroup().enumerate(allThreads, true);
			for(int i = 0; i < allThreads.length; i++) {
				if(allThreads[i] != null && allThreads[i] != myThread) {
					try {
						allThreads[i].join();
					} catch (InterruptedException e) {
					}
				}
			}
		}
		
	}
}
