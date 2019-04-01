package jrds.agent;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Start implements Serializable {

    private static final int DEFAULTPORT = 2002;
    private static final String DEFAULTPROTOCOL = "rmi";
    public enum PROTOCOL {
        rmi,
        jmx,
        jmxmp,
        jolokia,
    }

    public static final RProbeActor actor = new RProbeActor();

    private static final class JrdsMBeanInfo {
        MBeanServer mbs;
        JMXServiceURL url;
        JMXConnectorServer cs;

        public JrdsMBeanInfo(PROTOCOL protocol, String host, int port) throws IOException {
            String path = "/";
            String protocolString = protocol.toString();
            if (protocol == PROTOCOL.jmx) {
                protocolString = "rmi";
                java.rmi.registry.LocateRegistry.createRegistry(port);
                path = "/jndi/rmi://" + host + ":" + port + "/jmxrmi";
            }
            url = new JMXServiceURL(protocolString, host, port, path);
            mbs = ManagementFactory.getPlatformMBeanServer();
            cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
            cs.start();
            // load code before the security manager is started
            JMXServiceURL addr = cs.getAddress();
            JMXConnectorFactory.connect(addr).close();
        }
    }

    private static final Map<Number, Constructor<? extends Number>> constructors = new HashMap<>();

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String portProp = System.getProperty("jrds.port");
        int port = parseStringNumber(portProp, DEFAULTPORT);
        if(port == 0)
            port = DEFAULTPORT;

        PROTOCOL proto = PROTOCOL.valueOf(DEFAULTPROTOCOL);
        String protoProp = System.getProperty("jrds.proto", proto.toString()).trim().toLowerCase();
        if( ! "".equals(protoProp) )
            proto = PROTOCOL.valueOf(protoProp);

        start(port, proto);

        //Initialization done, set the security manager
        String withSecurity = System.getProperty("jrds.security", "true");
        if (System.getSecurityManager() == null && Boolean.parseBoolean(withSecurity)) {
            boolean debugPerm = false;
            if(System.getProperty("jrds.debugperm") != null) {
                debugPerm = true;
            }
            System.setSecurityManager( new AgentSecurityManager(debugPerm, proto) );
        }

        //Make it wait on himself to wait forever
        try {
            Thread.currentThread().join();
            System.out.print("joined");
        } catch (InterruptedException e) {
        }
    }

    /**
     * start the listen thread with a predefined port and security manager, so jrdsagent can be used as a lib and not a standalone daemon
     * @param port
     * @param proto
     */
    public static void start(int port, PROTOCOL proto) {
        try {
            switch(proto) {
            case jolokia: 
                RProbeJolokiaImpl.register(actor, port);
                break;
            case rmi: 
                RProbeRMIImpl.register(actor, port);
                break;
            case jmxmp:
            case jmx:
                new JrdsMBeanInfo(proto, "localhost", port);
                RProbeJMXImpl.register(actor);
                break;
            }
            //Check if actor can read uptime
            actor.getUptime();
        } catch (InvocationTargetException e) {
            throw new CollectException("failed to start jrdsagent", e.getCause());
        } catch (Exception e) {
            throw new CollectException("failed to start jrdsagent", e);
        }
    }

    /**
     * <p>A compact and exception free number parser.<p>
     * <p>If the string can be parsed as the specified type, it return the default value</p>
     * @param toParse The string to parse
     * @param defaultVal A default value to use it the string can't be parsed
     * @return An Number object using the same type than the default value.
     */
    @SuppressWarnings("unchecked")
    public static <NC extends Number> NC parseStringNumber(String toParse, NC defaultVal) {
        toParse = toParse != null ? toParse.trim() : null;
        if(toParse == null || toParse.isEmpty()) {
            return defaultVal;
        }

        try {
            Constructor<? extends Number> c;
            if (constructors.containsKey(defaultVal)) {
                c = constructors.get(defaultVal);
            } else {
                // Does a permission check, so avoid it if possible
                Class<? extends Number> clazz = defaultVal.getClass();
                c = (Constructor<? extends Number>) clazz.getConstructor(String.class);
                constructors.put(defaultVal, c);
            }
            // Integer.valueOf and Long.valueOf can reuse the value cache, less object allocation
            // .newInstance does many check, avoid them if possible
            switch (c.getDeclaringClass().getSimpleName()) {
            case "Integer":
                return (NC) Integer.valueOf(toParse);
            case "Long":
                return (NC) Long.valueOf(toParse);
            case "Double":
                return (NC) Double.valueOf(toParse);
            case "Float":
                return (NC) Float.valueOf(toParse);
            default:
                return (NC) c.newInstance(toParse);
            }
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException |
                        InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return defaultVal;
        }
    }

}
