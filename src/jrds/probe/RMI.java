package jrds.probe;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jrds.ProbeConnected;
import jrds.agent.RProbe;
import jrds.factories.ProbeMeta;

import org.apache.log4j.Level;

@ProbeMeta(
        topStarter=jrds.probe.JRDSSocketFactory.class
        )
public class RMI extends ProbeConnected<String, Number, AgentConnection> {
    List<?> args = new ArrayList<Object>(0);
    private String remoteName = null;
    Map<String, String> remoteSpecifics = Collections.emptyMap();

    public RMI() {
        super(AgentConnection.CONNECTIONNAME);
    }

    /**
     * A generic configurator that pass directly the elements of a list to the remote probe
     * @param args the argument of the remote probe
     * @return true if configuration succeeds
     */
    public Boolean configure(List<?> args) {
        if(!configure()) {
            return false;
        }
        setArgs(args);

        return true;
    }

    public Boolean configure() {
        // Extract all the list of remote properties of the remote probe
        // and store them in the remoteProperties set
        String remoteSpecificsNames = getPd().getSpecific("remoteSpecifics");
        if(remoteSpecificsNames != null && ! remoteSpecificsNames.trim().isEmpty()) {
            remoteSpecifics = new HashMap<String, String>();
            for(String remoteSpecific: remoteSpecificsNames.split(",")) {
                String trimed = remoteSpecific.trim();
                if(trimed.isEmpty()) {
                    continue;
                }
                remoteSpecifics.put(trimed, getPd().getSpecific(trimed));
            }
        }

        return true;
    }

    public Map<String, Number> getNewSampleValuesConnected(AgentConnection cnx) {
        Map<String, Number> retValues = null;
        try {
            RProbe rp = (RProbe) cnx.getConnection();
            remoteName = rp.prepare(getPd().getSpecific("remote"), remoteSpecifics, args);
            retValues = rp.query(remoteName);
        } catch (RemoteException e) {
            Throwable root = e;
            while(root.getCause() != null) {
                root = root.getCause();
            }
            log(Level.ERROR, root, "Remote exception on server: %s", root);
        } catch (InvocationTargetException e) {
            Throwable root = e;
            while(root.getCause() != null) {
                root = root.getCause();
            }
            log(Level.ERROR, root, "Failed to prepare %s: %s", this, root);
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
}
