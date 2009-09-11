package jrds.probe;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import jrds.RdsHost;
import jrds.agent.RProbe;
import jrds.starter.Resolver;
import jrds.starter.SocketFactory;
import jrds.starter.Starter;
import jrds.starter.StarterNode;
import jrds.starter.StartersSet;

public class RMIStarter extends Starter {
	static final private Logger logger = Logger.getLogger(RMIStarter.class);
	private RdsHost host;
	private Starter resolver = null;
	private int port;
	private Registry registry = null;
	private RProbe rp = null;
	
	RMIStarter(RdsHost host, int port) {
		this.host = host;
		this.port = port;
		resolver = host.getStarters().find(Resolver.makeKey(host));
	}
	public Object getKey() {
		return "rmi://" + host + ":"  + port;
	}
	public boolean start() {
		boolean started = false;
		if(resolver.isStarted()) {
			try {
				SocketFactory sf = (SocketFactory) getLevel().find(SocketFactory.class);
				registry = LocateRegistry.getRegistry(host.getName(), port, new JRDSSocketFactory(sf));
				rp = (RProbe) registry.lookup(RProbe.NAME);
				setUptime(rp.getUptime());
				started = true;
			} catch (RemoteException e) {
				logger.error("Remote exception on server " + host + ": " + e.getCause());
			} catch (NotBoundException e) {
				logger.error("Unattended exception: ", e);
			}
		}
		return started;
	}
	public void stop() {
		logger.debug("Stopping RMIStarter for " + host + ":" + port);
		rp = null;
		registry = null;
	}
	/* (non-Javadoc)
	 * @see jrds.Starter#Register(jrds.StarterNode)
	 */
	public Starter register(StarterNode node) {
		logger.trace("Registring with " + node);
		StartersSet ss = node.getStarters();
		if(ss.find(getKey()) == null)
			super.register(node);
		return ss.find(getKey());
	}
	/**
	 * @return the rp
	 */
	public RProbe getRp() {
		return rp;
	}

}
