package jrds.agent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public final static class JrdsLogHandler implements LogHandler {

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

    };

    static public final class RemoteNamingException extends RemoteException {
        public RemoteNamingException(String string, NameNotFoundException e) {
            super(string, e);
        }
    };

    public final static String NAME = "jrds:type=agent";
    private static JolokiaServer server;
    public static final String JOLOKIA_AGENT_URL = "jolokia.agent";

    static public final void register(RProbeActor actor, int port) throws InvocationTargetException {
        try {
            Map<String,String> config = new HashMap<String, String>();
            config.put("port", String.valueOf(port));
            config.put("discoveryEnabled", "false");
            config.put("host", "*");
            config.put("logHandlerClass", JrdsLogHandler.class.getName());
            JvmAgentConfig pConfig = new JvmAgentConfig(config);
            server = new JolokiaServer(pConfig, false);
            server.start();
            String url = server.getUrl();
            System.setProperty(JOLOKIA_AGENT_URL, url);
            
            RProbeJMXImpl.registerinstance(new RProbeJolokiaImpl(actor));
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
