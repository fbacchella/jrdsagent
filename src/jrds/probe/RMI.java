package jrds.probe;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrds.Probe;
import jrds.RdsHost;
import jrds.agent.RProbe;

import org.apache.log4j.Logger;

public class RMI extends Probe {
	static final private Logger logger = Logger.getLogger(RMI.class);
	static final private int PORT = 2002;
	List<?> args = new ArrayList<Object>(0);
	private String remoteName = null;

	RMIStarter localstarter = null;

	/* (non-Javadoc)
	 * @see jrds.Probe#setHost(jrds.RdsHost)
	 */
	@Override
	public void setHost(RdsHost monitoredHost) {
		super.setHost(monitoredHost);
		localstarter = (RMIStarter) new RMIStarter(monitoredHost, PORT).register(monitoredHost);
	}

	protected RProbe init() {
		RProbe rp = null;
		try {
			rp = (RProbe) localstarter.getRp();
			if( ! rp.exist(remoteName))
				remoteName = rp.prepare(getPd().getSpecific("remote"), args);
		} catch (RemoteException e) {
			rp = null;
			logger.error("Remote exception on server with probe " + this + ": " + e.getCause());
		}
		return rp;
	}

	public Map<?,?> getNewSampleValues() {
		Map<?,?> retValues = new HashMap<Object, Object>(0);
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

	public List<?> getArgs() {
		return args;
	}

	public void setArgs(List<?> l) {
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
