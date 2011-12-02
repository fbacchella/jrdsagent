package jrds.agent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * A intermediate class for RProbe API, mainly to translate exceptions
 * @author bacchell
 *
 */
public class RProbeImpl extends UnicastRemoteObject implements RProbe, Serializable {
    static final long serialVersionUID = -7914792289084645089L;

    final private RProbe rprobe;

    public RProbeImpl() throws RemoteException {
        super();
        rprobe = RProbeActor.getInstance();
    }

    public RProbeImpl(int port) throws RemoteException {
        super(port);
        rprobe = RProbeActor.getInstance();
    }

    /**
     * @param name
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#query(java.lang.String)
     */
    public Map<String, Number> query(String name) throws RemoteException {
        try {
            return rprobe.query(name);
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + name, e);
        }
    }

    /**
     * @param name
     * @param args
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#prepare(java.lang.String, java.util.List)
     */
    public String prepare(String name, List<?> args) throws RemoteException {
        try {
            return rprobe.prepare(name, args);
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + name, e);
        }
    }

    /**
     * @param name
     * @param statFile
     * @param args
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#prepare(java.lang.String, java.lang.String, java.util.List)
     */
    public String prepare(String name, String statFile, List<?> args)
            throws RemoteException {
        try {
            return rprobe.prepare(name, statFile, args);
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
            return rprobe.getUptime();
        } catch (Exception e) {
            throw new RemoteException("Error while getting uptime for " + rprobe, e);
        }
    }

}
