package jrds.probe;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import jrds.agent.RProbe;
import jrds.starter.Connection;
import jrds.starter.Resolver;
import jrds.starter.SocketFactory;
import jrds.starter.Starter;

public class RMIConnection extends Connection {
	static final private Logger logger = Logger.getLogger(RMIConnection.class);
	private int port = 2002;
	private Registry registry = null;
	private RProbe rp = null;

	public RMIConnection() {
	}

	public RMIConnection(Integer port) {
		this.port = port.intValue();
	}

	@Override
	public Object getConnection() {
		return rp;
	}

	@Override
	public long setUptime() {
		try {
			return rp.getUptime();
		} catch (RemoteException e) {
			logger.error("Remote exception on server " + getHostName() + ": " + e.getCause());
			return 0;
		}
	}

	@Override
	public boolean startConnection() {
		String hostName = getHostName();
		if(logger.isDebugEnabled()) 
			logger.debug("Starting RMIStarter for " + hostName + ":" + port);
		Starter resolver = getLevel().find(Resolver.makeKey(hostName));
		boolean started = false;
		if(resolver.isStarted()) {
			try {
				SocketFactory sf = (SocketFactory) getLevel().find(SocketFactory.class);
				registry = LocateRegistry.getRegistry(hostName, port, new JRDSSocketFactory(sf));
				rp = (RProbe) registry.lookup(RProbe.NAME);
				started = true;
			} catch (RemoteException e) {
				logger.error("Remote exception on server " + hostName + ": " + e.getCause());
			} catch (NotBoundException e) {
				logger.error("Unattended exception: ", e);
			}
		}
		return started;
	}

	@Override
	public void stopConnection() {
		if(logger.isDebugEnabled()) 
			logger.debug("Stopping RMIStarter for " + getHostName() + ":" + port);
		rp = null;
		registry = null;
	}

}
