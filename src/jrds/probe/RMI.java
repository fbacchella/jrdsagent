package jrds.probe;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrds.ConnectedProbe;
import jrds.Probe;
import jrds.agent.RProbe;

import org.apache.log4j.Logger;

public class RMI extends Probe<String, Number> implements ConnectedProbe {
	static final private Logger logger = Logger.getLogger(RMI.class);
	List<?> args = new ArrayList<Object>(0);
	private String remoteName = null;
	private String connectionName = RMIConnection.class.getName();

	public boolean configure() {
		RMIConnection cnx = (RMIConnection) getStarters().find(connectionName);
		if(cnx == null) {
			logger.error("No RMI connection configured for " + this + " with name " + connectionName);
			return false;
		}
		return true;
	}
	
	protected RProbe init() {
		RMIConnection cnx = (RMIConnection) getStarters().find(connectionName);
		if(cnx == null) {
			logger.warn("No connection found for " + this + " with name " + getConnectionName());
		}
		if( !cnx.isStarted()) {
			return null;
		}
		//Uptime is collected only once, by the connexion
		setUptime(cnx.getUptime());

		RProbe rp = null;
		try {
			rp = (RProbe) cnx.getConnection();
			if( rp != null && ! rp.exist(remoteName))
				remoteName = rp.prepare(getPd().getSpecific("remote"), args);
		} catch (RemoteException e) {
			rp = null;
			if(logger.isDebugEnabled()) {
				Throwable root = e.getCause().getCause();
				if(root == null)
					root = e.getCause();
				logger.error("Remote exception on server with probe " + this + ": " + root, root);
			}
			else
				logger.error("Remote exception on server with probe " + this + ": " + e.getCause());
		}
		return rp;
	}

	public Map<String, Number> getNewSampleValues() {
		Map<String, Number> retValues = new HashMap<String, Number>(0);
		RProbe rp = init();
		try {
			if(rp != null)
				retValues = rp.query(remoteName);
		} catch (RemoteException e) {
			if(logger.isDebugEnabled()) {
				Throwable root = e.getCause().getCause();
				if(root == null)
					root = e.getCause();
				logger.error("Remote exception on server with probe " + this + ": " + root, root);
			}
			else
				logger.error("Remote exception on server with probe " + this + ": " + e.getCause());
		}
		return retValues;
	}

	public List<?> getArgs() {
		return args;
	}

	public void setArgs(List<?> l) {
		this.args = l;
	}

	public String getSourceType() {
		return "JRDS Agent";
	}

	public String getConnectionName() {
		return connectionName;
	}
	
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
}
