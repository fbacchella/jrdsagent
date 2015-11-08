package jrds.agent;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.Permission;
import java.security.Permissions;
import java.util.Arrays;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Start implements Serializable {
    static final private int defaultPort = 2002;
    static final private String defaultProto = "rmi";
    public static enum PROTOCOL {
        rmi,
        jmx,
        jmxmp,
    }

    static private final Permissions allowed = new Permissions();

    static {
        try {
            String[][] permArgs = new String[][] {
                    new String[] { "java.io.FilePermission", "/proc/*", "read" },
                    new String[] { "java.io.FilePermission", "/proc/net/*", "read" },
                    new String[] { "java.io.SerializablePermission", "enableSubstitution" },
                    new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
                    new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect.misc" },
                    new String[] { "java.lang.RuntimePermission", "accessDeclaredMembers" },
                    new String[] { "java.lang.RuntimePermission", "createClassLoader" },
                    new String[] { "java.lang.RuntimePermission", "getClassLoader" },
                    new String[] { "java.lang.RuntimePermission", "getProtectionDomain" },
                    new String[] { "java.lang.RuntimePermission", "loadLibrary.net" },
                    new String[] { "java.lang.RuntimePermission", "readFileDescriptor" },
                    new String[] { "java.lang.RuntimePermission", "modifyThreadGroup" },
                    new String[] { "java.lang.RuntimePermission", "modifyThread" },
                    new String[] { "java.lang.RuntimePermission", "reflectionFactoryAccess" },
                    new String[] { "java.lang.RuntimePermission", "setContextClassLoader" },
                    new String[] { "java.lang.RuntimePermission", "sun.rmi.runtime.RuntimeUtil.getInstance" },
                    new String[] { "java.lang.RuntimePermission", "writeFileDescriptor" },
                    new String[] { "java.lang.reflect.ReflectPermission", "suppressAccessChecks" },
                    new String[] { "java.net.NetPermission", "getProxySelector" },
                    new String[] { "java.net.NetPermission", "specifyStreamHandler" },
                    new String[] { "java.net.SocketPermission", "*", "listen,accept,resolve" },
                    new String[] { "java.security.SecurityPermission", "getPolicy" },
                    new String[] { "java.util.logging.LoggingPermission", "control", "" },
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.RMIClassLoaderSpi", "read" },
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.codebase", "read" },
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.hostname", "read" },
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.randomIDs", "read" },
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.useCodebaseOnly", "read" },
                    new String[] { "java.util.PropertyPermission", "java.util.Arrays.useLegacyMergeSort", "read" },
                    new String[] { "java.util.PropertyPermission", "jdk.internal.lambda.dumpProxyClasses", "read" },
                    new String[] { "java.util.PropertyPermission", "jdk.logging.allowStackWalkSearch", "read" },
                    new String[] { "java.util.PropertyPermission", "jdk.net.ephemeralPortRange.high", "read" },
                    new String[] { "java.util.PropertyPermission", "jdk.net.ephemeralPortRange.low", "read" },
                    new String[] { "java.util.PropertyPermission", "socksProxyHost", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.io.serialization.extendedDebugInfo", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.net.maxDatagramSockets", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.rmi.dgc.ackTimeout", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.rmi.loader.logLevel", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.rmi.transport.connectionTimeout", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.rmi.transport.tcp.handshakeTimeout", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.rmi.transport.tcp.responseTimeout", "read" },
                    new String[] { "java.util.PropertyPermission", "sun.util.logging.disableCallerCheck", "read" },
                    new String[] { "javax.management.MBeanPermission", "-#-[-]", "getClassLoaderRepository" },
                    new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#prepare[jrds:type=agent]", "invoke" },
                    new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#query[jrds:type=agent]", "invoke" },
                    new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#-[jrds:type=agent]", "getClassLoaderFor" },
                    new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#-[jrds:type=agent]", "getClassLoaderFor" },
                    new String[] { "javax.management.MBeanPermission", "sun.management.RuntimeImpl#Uptime[java.lang:type=Runtime]", "getAttribute" },
            };
            Class<?>[][] typeVector = new Class[][]{
                    new Class[] { String.class },
                    new Class[] { String.class, String.class },                    
            };
            for(String[] a: permArgs) {
                String className = a[0];
                String[] argVector = Arrays.copyOfRange(a, 1, a.length);
                Class<?> cl = Start.class.getClassLoader().loadClass(className);
                Constructor<?> c = cl.getConstructor( typeVector[argVector.length - 1]);
                Permission newPerm = (Permission) c.newInstance((Object[])argVector);
                allowed.add(newPerm);
            }
            
            //Add the java home to readable files
            Permission newPerm = new FilePermission(System.getProperty("java.home") + File.separator + "-", "read");
            allowed.add(newPerm);
            allowed.setReadOnly();
        } catch (Exception e) {
            throw new RuntimeException("Permission initialization failed: " + e.getMessage(), e);
        }
    }

    static private final class JrdsMBeanInfo {
        MBeanServer mbs;
        JMXServiceURL url;
        JMXConnectorServer cs;

        public JrdsMBeanInfo(PROTOCOL protocol, String host, int port) throws IOException, NotBoundException {
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
            JMXServiceURL addr = cs.getAddress();
            JMXConnectorFactory.connect(addr);
        }
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String portProp = System.getProperty("jrds.port");
        int port = parseStringNumber(portProp, Integer.class, defaultPort).intValue();
        if(port == 0)
            port = defaultPort;

        PROTOCOL proto = PROTOCOL.valueOf(defaultProto);
        String protoProp = System.getProperty("jrds.proto", proto.toString()).trim().toLowerCase();
        if( ! "".equals(protoProp) )
            proto = PROTOCOL.valueOf(protoProp);

        start(port, proto);

        //Initialization done, set the security manager
        String withSecurity = System.getProperty("jrds.security", "true");
        if (System.getSecurityManager() == null && Boolean.parseBoolean(withSecurity))
            System.setSecurityManager ( getSecurityManager() );

        //Make it wait on himself to wait forever
        try {
            Thread.currentThread().join();
            System.out.print("joined");
        } catch (InterruptedException e) {
        }
    }

    static final private SecurityManager getSecurityManager() {
        return new SecurityManager() {
            public void checkPermission(Permission perm) {
                if(allowed.implies(perm)) {
                    return;
                }
                super.checkPermission(perm);
            }
        };    
    }

    /**
     * start the listen thread with a predefined port and security manager, so jrdsagent can be used as a lib and not a standalone daemon
     * @param port
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public static void start(int port, PROTOCOL proto) {
        RProbeActor actor = new RProbeActor();
        try {
            switch(proto) {
            case  rmi: 
                RProbeRMIImpl.register(actor, port);
                break;
            case jmxmp:
            case jmx:
                new JrdsMBeanInfo(proto, "localhost", port);
                RProbeJMXImpl.register(actor);
                break;
            }
            //Check if actor can read uptime;
            actor.getUptime();
        } catch (Exception e) {
            throw new RuntimeException("failed to start jrdsagent", e);
        }
    }

    /**
     * A function from jrds.Util
     * @param toParse
     * @param numberClass
     * @param defaultVal
     * @return
     */
    public static Number parseStringNumber(String toParse, Class<? extends Number> numberClass, Number defaultVal) {
        if(toParse == null || "".equals(toParse))
            return defaultVal;
        if(! (Number.class.isAssignableFrom(numberClass))) {
            return defaultVal;
        }

        try {
            Constructor<? extends Number> c = numberClass.getConstructor(String.class);
            Number n = c.newInstance(toParse);
            return n;
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return defaultVal;
    }

}
