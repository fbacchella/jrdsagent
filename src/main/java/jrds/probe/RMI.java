package jrds.probe;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.slf4j.event.Level;

import jrds.ProbeConnected;
import jrds.Util;
import jrds.agent.CollectException;
import jrds.agent.RProbe;
import jrds.factories.ProbeMeta;

@ProbeMeta(timerStarter=JmxSocketFactory.class)
public class RMI extends ProbeConnected<String, Number, AgentConnection> {
    private List<Object> args = new ArrayList<>(0);
    private String remoteName = null;
    private Map<String, String> remoteSpecifics = Collections.emptyMap();

    public RMI() {
        super(AgentConnection.CONNECTIONNAME);
    }

    /**
     * A generic configurator that pass directly the elements of a list to the remote probe
     * @param args the argument of the remote probe
     * @return true if configuration succeeds
     */
    public Boolean configure(List<Object> args) {
        if (!configure()) {
            return false;
        }
        setArgs(args);

        return true;
    }

    @Override
    public Boolean configure() {
        // Extract all the list of remote properties of the remote probe
        // and store them in the remoteProperties set
        String remoteSpecificsNames = getPd().getSpecific("remoteSpecifics");
        if (remoteSpecificsNames != null && ! remoteSpecificsNames.trim().isEmpty()) {
            remoteSpecifics = new HashMap<>();
            for (String remoteSpecific: remoteSpecificsNames.split(",")) {
                String trimed = remoteSpecific.trim();
                if (trimed.isEmpty()) {
                    continue;
                }
                remoteSpecifics.put(trimed, Util.parseTemplate(getPd().getSpecific(trimed), this));
            }
        }
        log(Level.DEBUG, "remote specifics %s", remoteSpecifics);
        log(Level.DEBUG, "remote args for this probe: %s", args);

        return true;
    }

    public Map<String, Number> getNewSampleValuesConnected(AgentConnection cnx) {
        Map<String, Number> retValues = null;
        // a loop because if one query fails, we try again after a prepare
        for (int step = 0; step < 2; step++) {
            try {
                RProbe rp = cnx.getConnection();
                if(remoteName == null) {
                    log(Level.DEBUG, "name not found, needs to be reconfigure, iteration %d", step);
                    step++;
                    remoteName = rp.prepare(getPd().getSpecific("remote"), remoteSpecifics, args);
                    cnx.toBatch(remoteName);
                }
                Optional<Map<String, Number>> optionalValues = cnx.getValuesFromBatch(remoteName);
                if (optionalValues.isEmpty()) {
                    optionalValues = Optional.of(rp.query(remoteName));
                }
                retValues = optionalValues.get();
                break;
            } catch (RemoteException e) {
                cnx.unbatch(remoteName);
                Throwable root = e;
                while (root.getCause() != null) {
                    root = root.getCause();
                    if (root instanceof CollectException) {
                        break;
                    }
                }
                if (root instanceof NameNotFoundException) {
                    log(Level.DEBUG, "remote name '%s' not defined, needs to prepare", ((NameNotFoundException)root).getRemainingName().get(0));
                    remoteName = null;
                } else if (root instanceof EOFException) {
                    log(Level.ERROR, "Remote connection closed");
                } else if (root instanceof CollectException) {
                    log(Level.ERROR, "Collect failed on server: %s", root.getMessage());
                } else {
                    log(Level.ERROR, root, "Remote exception on server: %s", root);
                    break;
                }
            } catch (InvocationTargetException e) {
                cnx.unbatch(remoteName);
                Throwable root = e;
                while (root.getCause() != null) {
                    root = root.getCause();
                }
                log(Level.ERROR, root, "Failed to prepare %s: %s", this, root);
                break;
            }
        }
        return retValues;
    }

    protected List<Object> getArgs() {
        return args;
    }

    protected void setArgs(List<Object> l) {
        this.args = l;
    }

    public String getSourceType() {
        return "JRDS Agent";
    }

}
