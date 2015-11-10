package jrds.probe;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import jrds.PropertiesManager;
import jrds.agent.RProbe;
import jrds.factories.ProbeBean;
import jrds.starter.Resolver;
import jrds.starter.Starter;

import org.apache.log4j.Level;

@ProbeBean({"port"})
public class RMIConnection extends AgentConnection {

    private static final int AGENTPORT = 2002;
    private int port;
    private Registry registry = null;
    private RProbe rp = null;

    public RMIConnection() {
        this.port = AGENTPORT;
    }

    public RMIConnection(Integer port) {
        this.port = port;
    }

    @Override
    public RProbe getConnection() {
        return rp;
    }

    @Override
    public boolean startConnection() {
        String hostName = getHostName();
        log(Level.DEBUG, "Starting RMIStarter for %s:%d", hostName, port);
        Starter resolver = getLevel().find(Resolver.class);
        boolean started = false;
        if(resolver.isStarted()) {
            try {
                log(Level.TRACE, "locate registry for %s:%d", hostName, port);
                log(Level.TRACE, "will use %s for the socket factoy", getLevel().find(JRDSSocketFactory.class));
                registry = LocateRegistry.getRegistry(hostName, port, getLevel().find(JRDSSocketFactory.class));
                log(Level.TRACE, "lookup  probe %s", RProbe.NAME);

                rp = (RProbe) registry.lookup(RProbe.NAME);
                log(Level.TRACE, "done: %s", rp.getClass());
                started = true;
            } catch (NoSuchObjectException e) {
                log(Level.ERROR, e, "Remote exception on server %s: %s", getHostName(), e.getMessage());
            } catch (RemoteException e) {
                log(Level.ERROR, e, "Remote exception on server %s: %s", hostName, e.getCause());
            } catch (NotBoundException e) {
                log(Level.ERROR, e, "Unexpected exception: %s", e);
            }
        }
        return started && super.startConnection();
    }

    @Override
    public void stopConnection() {
        super.stopConnection();
        log(Level.DEBUG, "Stopping RMIStarter for %s:%d", getHostName(), port);
        rp = null;
        registry = null;
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#configure(jrds.PropertiesManager)
     */
    @Override
    public void configure(PropertiesManager pm) {
        super.configure(pm);
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", Integer.toString(pm.timeout * 1000));
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", Integer.toString(pm.timeout * 1000));
        System.setProperty("sun.rmi.transport.connectionTimeout", Integer.toString(pm.timeout * 1000));
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", Integer.toString(pm.timeout * 1000));
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

}
