package jrds.agent;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * A intermediate class for RProbe API, mainly to translate exceptions
 * @author bacchell
 *
 */
public class RProbeRMIImpl extends UnicastRemoteObject implements RProbe, Serializable {
    static final long serialVersionUID = -7914792289084645089L;

    final private RProbeActor actor;

    //RProbeRMIImpl should not be eligible to gc, so keep it un a useless variable
    private static RProbe dispatcher;

    static public final void register(RProbeActor actor, int port) throws InvocationTargetException {
        try {
            dispatcher = new RProbeRMIImpl(port, actor);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(RProbe.NAME, dispatcher);
        } catch (RemoteException e) {
            throw new InvocationTargetException(e, "Error registring RMI");
        } catch (AlreadyBoundException e) {
            throw new InvocationTargetException(e, "Error registring RMI");
        }
    }

    private RProbeRMIImpl(RProbeActor actor) throws RemoteException {
        super();
        this.actor = actor;
    }

    private RProbeRMIImpl(int port, RProbeActor actor) throws RemoteException {
        super(port);
        this.actor = actor;
    }

    /**
     * @param name
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#query(java.lang.String)
     */
    public Map<String, Number> query(String name) throws RemoteException {
        try {
            return actor.query(name);
        } catch (Exception e) {
            throw new RemoteException("Error while quering " + name, e);
        }
    }

    /**
     * @param name
     * @param args
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#prepare(String, Map, List)
     */
    public String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException {
        try {
            return actor.prepare(name, specifics, args);
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + name, e);
        }
    }

    /**
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#getUptime()
     */
    public long getUptime() throws RemoteException {
        try {
            return actor.getUptime();
        } catch (Exception e) {
            throw new RemoteException("Error while getting uptime for " + actor, e);
        }
    }

}
