package jrds.agent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.jolokia.jvmagent.JolokiaServer;
import org.jolokia.jvmagent.JvmAgentConfig;

public class RProbeJolokiaImpl extends StandardMBean implements RProbe {

    public final static String NAME = "jrds:type=agent";
    private final RProbeActor actor;
    private static JolokiaServer server;
    public static final String JOLOKIA_AGENT_URL = "jolokia.agent";

    static public final void register(RProbeActor actor, int port) throws InvocationTargetException {
        try {
            Map<String,String> config = new HashMap<String, String>();
            config.put("port", String.valueOf(port));
            config.put("discoveryEnabled", "false");
            JvmAgentConfig pConfig = new JvmAgentConfig(config);
            server = new JolokiaServer(pConfig, false);

            server.start();
            String url = server.getUrl();
            System.setProperty(JOLOKIA_AGENT_URL, url);
            
            RProbeJMXImpl.register(actor);
            
            System.out.println("Jolokia: Agent started with URL " + server.getUrl());
        } catch (RuntimeException exp) {
            System.err.println("Could not start Jolokia agent: " + exp);
        } catch (IOException exp) {
            System.err.println("Could not start Jolokia agent: " + exp);
        }
    }
    
    static public final void stop() {
        server.stop();
        System.clearProperty(JOLOKIA_AGENT_URL);
    }

    protected RProbeJolokiaImpl(RProbeActor actor) throws NotCompliantMBeanException {
        super(jrds.agent.RProbe.class, false);
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

    /**
     * @param name
     * @param args
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#prepare(java.lang.String, java.util.List)
     */
    public String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException {
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

}
