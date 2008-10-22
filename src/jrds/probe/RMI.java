package jrds.probe;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrds.Probe;
import jrds.RdsHost;
import jrds.Starter;
import jrds.StarterNode;
import jrds.StartersSet;
import jrds.agent.RProbe;

import org.apache.log4j.Logger;

public class RMI extends Probe {
	static final private Logger logger = Logger.getLogger(RMI.class);
	static final private int PORT = 2002;
	static {
		try {
			RMISocketFactory.setSocketFactory(new JRDSSocketFactory());
		} catch (IOException e) {
			logger.fatal("Cannot register RMI Socket factory: " + e);
		}
	}
	List args = new ArrayList(0);
	private String remoteName = null;

	private class RMIStarter extends Starter {
		private RdsHost host;
		private Starter resolver = null;
		private int port = PORT;
		private Registry registry = null;
		private RProbe rp = null;
		RMIStarter(RdsHost host) {
			this.host = host;
			resolver = host.getStarters().find(Starter.Resolver.buildKey(host.getName()));
		}
		RMIStarter(RdsHost host, int port) {
			this.host = host;
			this.port = port;
			resolver = host.getStarters().find(Starter.Resolver.buildKey(host.getName()));
		}
		public Object getKey() {
			return "rmi://" + host + ":"  + port;
		}
		public boolean start() {
			boolean started = false;
			if(resolver.isStarted()) {
				try {
					registry = LocateRegistry.getRegistry(host.getName(), port);
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
			logger.debug("Stoping RMIStarter for " + host + ":" + port);
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
	};
	RMIStarter localstarter = null;

	public RMI() {
	}

	public void setHost(RdsHost monitoredHost) {
		super.setHost(monitoredHost);
		localstarter = new RMIStarter(monitoredHost, PORT);
		localstarter = (RMIStarter) localstarter.register(monitoredHost);
	}

	protected RProbe init() {
		RProbe rp = null;
		try {
			rp = (RProbe) localstarter.rp;
			if( ! rp.exist(remoteName))
				remoteName = rp.prepare(getPd().getSpecific("remote"), args);
		} catch (RemoteException e) {
			rp = null;
			logger.error("Remote exception on server with probe " + this + ": " + e.getCause());
		}
		return rp;
	}

	public Map getNewSampleValues() {
		Map retValues = new HashMap(0);
		if(localstarter.isStarted()) {
			RProbe rp = init();
			try {
				if(rp != null)
					retValues = rp.query(remoteName);
			} catch (RemoteException e) {
				logger.error("Remote exception on server with probe " + this + ": " + e.getCause());
			}
		}
		return retValues;
	}

	public List getArgs() {
		return args;
	}

	public void setArgs(List l) {
		this.args = l;
	}
	public boolean isCollectRunning() {
		return super.isCollectRunning() && localstarter.isStarted();
	}

	public String getSourceType() {
		return "JRDS Agent";
	}

	public long getUptime() {
		return localstarter.getUptime();
	}
}
