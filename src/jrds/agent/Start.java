package jrds.agent;

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
import java.security.SecurityPermission;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanPermission;
import javax.management.MBeanServer;
import javax.management.ObjectName;
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
        Map<String, ObjectName> objNameMap = new HashMap<String, ObjectName>();
        try {
            for(String onameString: new String[] {"jrds:type=agent", "java.lang:type=Runtime"}) {
                ObjectName on = new ObjectName(onameString);
                objNameMap.put(onameString, on);
            }

            Object[][] args = new Object[][]{
                    new Object[] {"-#-[-]", "getClassLoaderRepository"},
                    new Object[] {"jrds.agent.RProbeJMXImpl", null, objNameMap.get("jrds:type=agent"), "*"},
                    new Object[] {"jrds.agent.RProbeJMXImpl", "Uptime", objNameMap.get("jrds:type=agent"), "*"},
                    new Object[] {"jrds.agent.RProbeJMXImpl", "query", objNameMap.get("jrds:type=agent"), "*"},
                    new Object[] {"jrds.agent.RProbeJMXImpl", "prepare", objNameMap.get("jrds:type=agent"), "*"},
                    new Object[] {"sun.management.RuntimeImpl", "Uptime", objNameMap.get("java.lang:type=Runtime"), "getAttribute"},
            };
            Map<Integer, Constructor<MBeanPermission>> constructsmap = new HashMap<Integer, Constructor<MBeanPermission>>();

            @SuppressWarnings("unchecked")
            Constructor<MBeanPermission>[] mbpermConstructors = (Constructor<MBeanPermission>[]) MBeanPermission.class.getConstructors();
            for(Constructor<MBeanPermission> c: mbpermConstructors ) {
                constructsmap.put(c.getGenericParameterTypes().length, c);
            }
            for(Object[] arg: args) {
                MBeanPermission aperm = constructsmap.get(arg.length).newInstance(arg);
                allowed.add(aperm);
            }
            Map<Class<?>, String[]> permByName = new HashMap<Class<?>, String[]>();
            permByName.put(RuntimePermission.class, new String[] {
                "getFileSystemAttributes", 
                "readFileDescriptor", "writeFileDescriptor", //Don't forget, network sockets are file descriptors
                "modifyThreadGroup", "modifyThread", //Needed by termination of the VM
                "setContextClassLoader", "getClassLoader", "createClassLoader",
                "sun.rmi.runtime.RuntimeUtil.getInstance", "sun.misc.Perf.getPerf", "reflectionFactoryAccess",
                "accessDeclaredMembers", "fileSystemProvider", "getProtectionDomain",
                "accessClassInPackage.sun.util.resources", "accessClassInPackage.sun.instrument", "accessClassInPackage.sun.management", "accessClassInPackage.sun.management.resources",
                "accessClassInPackage.sun.util.logging.resources", "accessClassInPackage.sun.text.resources", "accessClassInPackage.com.sun.jmx.remote.internal",
                "accessClassInPackage.sun.security.provider", "accessClassInPackage.com.sun.jmx.remote.protocol.jmxmp", "accessClassInPackage.sun.reflect", "accessClassInPackage.sun.reflect.misc",

            });
            permByName.put(SecurityPermission.class, new String[] {
                "getPolicy", "getProperty.networkaddress.cache.ttl", "getProperty.networkaddress.cache.negative.ttl", 
                "getProperty.security.provider", "getProperty.securerandom.source", "putProviderProperty.SUN", 
                "getProperty.security.provider.1", "getProperty.security.provider.2", "getProperty.security.provider.3", "getProperty.security.provider.4",
                "getProperty.security.provider.5", "getProperty.security.provider.6", "getProperty.security.provider.7", "getProperty.security.provider.8",
                "getProperty.security.provider.9", "getProperty.security.provider.10", "getProperty.security.provider.11"
            });
            for(Map.Entry<Class<?>, String[]> e: permByName.entrySet()) {
                @SuppressWarnings("unchecked")
                Constructor<Permission> c = (Constructor<Permission>) e.getKey().getConstructor(String.class);
                for(String permName: e.getValue()) {
                    Permission newPerm;
                    newPerm = c.newInstance(permName);
                    allowed.add(newPerm);
                }
            }

            String[][] permArgs = new String[][] {
                    new String[] { "java.util.logging.LoggingPermission", "control", "" },
                    new String[] { "java.net.NetPermission", "getProxySelector"},
                    new String[] { "java.net.NetPermission", "specifyStreamHandler"},
                    new String[] { "javax.management.MBeanServerPermission", "*"},
                    new String[] { "java.lang.management.ManagementPermission", "monitor"},
                    new String[] { "java.lang.reflect.ReflectPermission", "suppressAccessChecks"},
                    new String[] { "java.io.SerializablePermission", "enableSubstitution"},
                    new String[] { "java.io.FilePermission", "/proc/-", "read"},
                    new String[] { "java.io.FilePermission", "/sys/-", "read"},
                    new String[] { "java.io.FilePermission", "/", "read"},
                    new String[] { "java.io.FilePermission", "/dev/random", "read"},
                    new String[] { "java.io.FilePermission", "/dev/urandom", "read"},
                    new String[] { "java.io.FilePermission", System.getProperty("java.home") + "/-", "read"},
                    new String[] { "java.util.PropertyPermission", "sun.net.maxDatagramSockets", "read"},
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.randomIDs", "read"},
                    new String[] { "java.util.PropertyPermission", "java.rmi.server.hostname", "read"},
                    new String[] { "java.util.PropertyPermission", "java.security.egd", "read"},
                    new String[] { "java.util.PropertyPermission", "socksProxyHost", "read"},
                    new String[] { "java.util.PropertyPermission", "jdk.logging.allowStackWalkSearch", "read"},
                    new String[] { "java.util.PropertyPermission", "sun.util.logging.disableCallerCheck", "read"},
                    new String[] { "java.util.PropertyPermission", "com.sun.jmx.remote.bug.compatible", "read"},
                    new String[] { "java.util.PropertyPermission", "os.arch", "read"},
                    new String[] { "java.util.PropertyPermission", "user.language.format", "read"},
                    new String[] { "java.util.PropertyPermission", "user.script.format", "read"},
                    new String[] { "java.util.PropertyPermission", "user.country.format", "read"},
                    new String[] { "java.util.PropertyPermission", "line.separator", "read"},
                    new String[] { "java.net.SocketPermission", "*", "accept,connect,listen,resolve"},
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
            allowed.setReadOnly();
        } catch (Exception e) {
            throw new RuntimeException("Permission initialization failed: " + e.getMessage(), e);
        }
    }

    static private final class JrdsMBeanInfo {
        MBeanServer mbs;
        JMXServiceURL url;
        JMXConnectorServer cs;

        public JrdsMBeanInfo(String protocol, String host, int port) throws IOException, NotBoundException {
            String path = "/";
            if (protocol == "jmx") {
                protocol = "rmi";
                java.rmi.registry.LocateRegistry.createRegistry(port);
                path = "/jndi/rmi://" + host + ":" + port + "/jmxrmi";
            }
            url = new JMXServiceURL(protocol, host, port, path);
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
        RProbeActor actor = RProbeActor.getInstance();
        try {
            switch(proto) {
            case  rmi: 
                RProbeRMIImpl.register(actor, port);
                break;
            case jmxmp:
            case jmx:
                new JrdsMBeanInfo(proto.toString(), "localhost", port);
                RProbeJMXImpl.register(actor);
                break;
            }
        } catch (InvocationTargetException e1) {
        } catch (IOException e) {
        } catch (NotBoundException e) {
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
