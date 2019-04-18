package jrds.agent;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;

import org.jolokia.jvmagent.JolokiaServer;
import org.jolokia.jvmagent.JvmAgentConfig;
import org.jolokia.util.LogHandler;

public class RProbeJolokiaImpl extends RProbeJMXImpl {

    public static final class JrdsLogHandler implements LogHandler {

        private static final Logger julilogger = Logger.getLogger(RProbeJolokiaImpl.class.getCanonicalName());
        @Override
        public void debug(String message) {
            julilogger.fine(message);
        }

        @Override
        public void info(String message) {
            julilogger.info(message);
        }

        @Override
        public void error(String message, Throwable t) {
            julilogger.log(Level.WARNING, message, t);
        }

    }

    public static final class RemoteNamingException extends RemoteException {
        public RemoteNamingException(String string, NameNotFoundException e) {
            super(string, e);
        }
    }

    private static JolokiaServer server;
    public static final String JOLOKIA_AGENT_URL = "jolokia.agent";

    public static final void register(RProbeActor actor, int port) throws InvocationTargetException {
        try {
            Map<String,String> config = new HashMap<>();
            config.put("port", String.valueOf(port));
            config.put("discoveryEnabled", "false");
            config.put("host", "*");
            config.put("logHandlerClass", JrdsLogHandler.class.getName());
            JvmAgentConfig pConfig = new JvmAgentConfig(config);
            server = new JolokiaServer(pConfig, false);
            server.start();
            String url = server.getUrl();
            System.setProperty(JOLOKIA_AGENT_URL, url);
            // Check that jolokia is started, before Security manager is started
            URL jolokiaurl = new URL(url + "read/" +RProbeJMXImpl.NAME + "/Uptime");
            try(InputStream cnx = jolokiaurl.openConnection().getInputStream()) {
                byte[] buffer = new byte[4096];
                while(cnx.read(buffer) >= 0) {
                }
            }
            RProbeJMXImpl.registerinstance(new RProbeJolokiaImpl(actor));
        } catch (RuntimeException | IOException ex) {
            throw new InvocationTargetException(ex, "Error registring Jolokia agent");
        }
    }

    public static final void stop() {
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
            throw new RemoteNamingException("Error while quering " + name, e);
        } catch (Exception e) {
            throw new RemoteException("Error while quering " + name, e);
        }
    }

}
