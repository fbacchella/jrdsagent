package jrds.probe;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
public class RMI extends ProbeConnected<String, Number, RMIConnection> {
    List<?> args = new ArrayList<Object>(0);
    private String remoteName = null;

    public RMI() {
        super(RMIConnection.class.getName());
    }

    public Map<String, Number> getNewSampleValuesConnected(RMIConnection cnx) {
        Map<String, Number> retValues = new HashMap<String, Number>(0);
        try {
            RProbe rp = (RProbe) cnx.getConnection();
            String statFile = getPd().getSpecific("statFile");
            if(statFile != null)
                remoteName = rp.prepare(getPd().getSpecific("remote"), statFile, args);
            else
                remoteName = rp.prepare(getPd().getSpecific("remote"), args);
            retValues = rp.query(remoteName);
        } catch (RemoteException e) {
            Throwable root = e;
            while(root.getCause() != null) {
                root = root.getCause();
            }
            log(Level.ERROR, root, "Remote exception on server: %s", root);
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
