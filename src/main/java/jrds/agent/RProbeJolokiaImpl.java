package jrds.agent;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NameNotFoundException;

import org.jolokia.jvmagent.JolokiaServer;
import org.jolokia.jvmagent.JvmAgentConfig;
import org.jolokia.server.core.config.ConfigKey;
import org.jolokia.server.core.config.Configuration;
import org.jolokia.server.core.service.impl.JulLogHandler;

public class RProbeJolokiaImpl extends RProbeJMXImpl {

    public static final class RemoteNamingException extends RemoteException {
        public RemoteNamingException(String string, NameNotFoundException e) {
            super(string, e);
        }
    }

    private static JolokiaServer server;
    public static final String JOLOKIA_AGENT_URL = "jolokia.agentUrl";
    public static final String JOLOKIA_AGENT_ID = "jolokia.agentId";

    public static void register(RProbeActor actor, int port) throws InvocationTargetException {
        RProbeJMXImpl.registerinstance(new RProbeJolokiaImpl(actor));
        try {
            Map<String,String> config = new HashMap<>();
            config.put("port", String.valueOf(port));
            config.put("discoveryEnabled", "false");
            config.put("host", "*");
            config.put("logHandlerClass", JulLogHandler.class.getName());
            JvmAgentConfig pConfig = new JvmAgentConfig(config);
            server = new JolokiaServer(pConfig);
            server.start(false);
            String url = server.getUrl();
            System.setProperty(JOLOKIA_AGENT_URL, url);
            Configuration configuration = pConfig.getJolokiaConfig();
            System.setProperty(JOLOKIA_AGENT_ID, configuration.getConfig(ConfigKey.AGENT_ID));
            // Check that jolokia is started, before Security manager is started
            URL jolokiaurl = new URL(url + "read/" + RProbeJMXImpl.NAME + "/Uptime");
            try (InputStream cnx = jolokiaurl.openConnection().getInputStream()) {
                byte[] buffer = new byte[4096];
                while(cnx.read(buffer) >= 0) {
                    // Read all input content
                }
            }
        } catch (RuntimeException | IOException ex) {
            throw new InvocationTargetException(ex, "Error registering Jolokia agent");
        }
    }

    public static void stop() {
        server.stop();
        System.clearProperty(JOLOKIA_AGENT_URL);
    }

    protected RProbeJolokiaImpl(RProbeActor actor) {
        super(actor);
    }

    @Override
    public Map<String, Number> query(String name) throws RemoteException {
        try {
            return actor.query(name);
        } catch (NameNotFoundException e) {
            throw new RemoteNamingException("Error while querying " + name, e);
        } catch (Exception e) {
            throw new RemoteException("Error while querying " + name, e);
        }
    }

}
