package jrds.agent;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.naming.NameNotFoundException;

import org.jolokia.jvmagent.JolokiaServer;
import org.jolokia.jvmagent.JvmAgentConfig;
import org.jolokia.util.LogHandler;

public class RProbeJolokiaImpl extends StandardMBean implements RProbe {
    
    static public final class RemoteExceptionNamingException extends RemoteException {
        public RemoteExceptionNamingException(String string, NameNotFoundException e) {

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
            super(string, e);
        }
    };

    public final static String NAME = "jrds:type=agent";
    private final RProbeActor actor;
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

            try {
                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                ObjectName name = new ObjectName(NAME);
                mbs.registerMBean(new RProbeJolokiaImpl(actor), name); 
            } catch (MalformedObjectNameException e) {
                throw new InvocationTargetException(e, "Error registring JMX");
            } catch (InstanceAlreadyExistsException e) {
                throw new InvocationTargetException(e, "Error registring JMX");
            } catch (MBeanRegistrationException e) {
                throw new InvocationTargetException(e, "Error registring JMX");
            } catch (NotCompliantMBeanException e) {
                throw new InvocationTargetException(e, "Error registring JMX");
            }
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
        } catch (NameNotFoundException e) {
            throw new RemoteExceptionNamingException("Error while quering " + name, e);
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
