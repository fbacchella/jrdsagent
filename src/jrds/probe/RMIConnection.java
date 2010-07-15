package jrds.probe;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import jrds.agent.RProbe;
import jrds.starter.Connection;
import jrds.starter.Resolver;
import jrds.starter.SocketFactory;
import jrds.starter.Starter;

import org.apache.log4j.Level;

public class RMIConnection extends Connection<RProbe> {
	private int port = 2002;
	private Registry registry = null;
	private RProbe rp = null;

	public RMIConnection() {
	}

	public RMIConnection(Integer port) {
		this.port = port.intValue();
	}

	@Override
	public RProbe getConnection() {
		return rp;
	}

	@Override
	public long setUptime() {
		try {
			log(Level.TRACE, "uptime: %d", rp.getUptime());
			return rp.getUptime();
		} catch (RemoteException e) {
			log(Level.ERROR, e, "Remote exception on server %s: %s", getHostName(), e.getCause());
			return 0;
		}
	}

	@Override
	public boolean startConnection() {
		String hostName = getHostName();
		log(Level.DEBUG, "Starting RMIStarter for %s:%d", hostName, port);
		Starter resolver = getLevel().find(Resolver.class);
		boolean started = false;
		if(resolver.isStarted()) {
			try {
				SocketFactory sf = (SocketFactory) getLevel().find(SocketFactory.class);
				log(Level.TRACE, "locate registry for %s:%d", hostName, port);
				registry = LocateRegistry.getRegistry(hostName, port, new JRDSSocketFactory(sf));
				log(Level.TRACE, "lookup  probe %s", RProbe.NAME);
				rp = (RProbe) registry.lookup(RProbe.NAME);
				log(Level.TRACE, "done: %s", rp);
				started = true;
			} catch (RemoteException e) {
				log(Level.ERROR, e, "Remote exception on server %s: %s", hostName, e.getCause());
			} catch (NotBoundException e) {
				log(Level.ERROR, e, "Unexpected exception: %s", e);
			}
		}
		return started;
	}

	@Override
	public void stopConnection() {
		log(Level.DEBUG, "Stopping RMIStarter for %s:%d", getHostName(), port);
		rp = null;
		registry = null;
	}

}
