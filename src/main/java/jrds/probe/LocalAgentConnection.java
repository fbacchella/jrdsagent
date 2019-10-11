package jrds.probe;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import jrds.PropertiesManager;
import jrds.agent.CollectException;
import jrds.agent.RProbe;
import jrds.agent.RProbeActor;
import jrds.agent.RProbeLocalImpl;

public class LocalAgentConnection extends AgentConnection {
    private final RProbe rp;

    public LocalAgentConnection() {
        rp = new RProbeLocalImpl(new RProbeActor());
        super.setName(RMIConnection.class.getName());
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIConnection#getConnection()
     */
    @Override
    public RProbe getConnection() {
        return rp;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIConnection#setUptime()
     */
    @Override
    public long setUptime() {
        try {
            return rp.getUptime();
        } catch (RemoteException | InvocationTargetException e) {
            Throwable root = e.getCause() != null ? e.getCause(): e;
            throw new CollectException("Unable to get uptime local: " + root.getMessage(), root);
        }
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIConnection#startConnection()
     */
    @Override
    public boolean startConnection() {
        return true;
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIConnection#stopConnection()
     */
    @Override
    public void stopConnection() {
    }

    /* (non-Javadoc)
     * @see jrds.probe.RMIConnection#configure(jrds.PropertiesManager)
     */
    @Override
    public void configure(PropertiesManager pm) {
        super.configure(pm);
    }

}
