package jrds.agent;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class RProbeLocalImpl implements RProbe {
    
    private final RProbeActor actor;

    public RProbeLocalImpl(RProbeActor actor) {
        this.actor = actor;
    }

    @Override
    public Map<String, Number> query(String name) throws RemoteException {
        try {
            return actor.query(name);
        } catch (Exception e) {
            throw new RemoteException("Error while quering " + name, e);
        }        
    }

    @Override
    public String prepare(String name, Map<String, String> specifics,
            List<?> args) throws RemoteException {
        try {
            return actor.prepare(name, specifics, args);
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + name, e);
        }
    }

    @Override
    public long getUptime() throws RemoteException {
        try {
            return actor.getUptime();
        } catch (Exception e) {
            throw new RemoteException("Error while getting uptime for " + actor, e);
        }
    }

    @Override
    public Map<String, Map<String, Number>> batch(List<String> names) throws RemoteException {
        try {
            return actor.batch(names);
        } catch (Exception e) {
            throw new RemoteException("Error while doing batch for " + actor, e);
        }
    }

}
