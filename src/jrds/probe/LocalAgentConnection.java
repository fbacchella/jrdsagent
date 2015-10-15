package jrds.probe;

import jrds.PropertiesManager;
import jrds.agent.RProbe;
import jrds.agent.RProbeActor;

public class LocalAgentConnection extends AgentConnection {
    private final RProbeActor rp;

    public LocalAgentConnection() {
        rp = new RProbeActor();
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
        return rp.getUptime();
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
