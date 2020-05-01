package jrds.probe;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

import org.slf4j.event.Level;

import jrds.PropertiesManager;
import jrds.agent.RProbe;
import jrds.starter.Resolver;
import jrds.starter.Starter;

public class RMIConnection extends AgentConnection {

    private Registry registry = null;
    private RProbe rp = null;

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
                RMIClientSocketFactory factory = getLevel().find(JmxSocketFactory.class).getFactory();
                log(Level.TRACE, "will use %s for the socket factoy", factory);
                registry = LocateRegistry.getRegistry(hostName, port, factory);
                log(Level.TRACE, "lookup  probe %s", RProbe.NAME);

                rp = (RProbe) registry.lookup(RProbe.NAME);
                log(Level.TRACE, "done: %s", rp.getClass());
                started = true;
            } catch (NoSuchObjectException e) {
                log(Level.ERROR, e, "Remote exception on server %s: %s", getHostName(), e);
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

}
