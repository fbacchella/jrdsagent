package jrds.agent;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.Permission;
import java.util.Collections;
import java.util.HashSet;

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
        //Make it wait on himself to wait forever
        try {
            Thread.currentThread().join();
            System.out.print("joined");
        } catch (InterruptedException e) {
        }
    }

    static final private SecurityManager getSecurityManager() {
        return new SecurityManager() {
            private final HashSet<String> runtimePermissions = new HashSet<String>();
            {
                Collections.addAll(runtimePermissions, "setContextClassLoader", "getFileSystemAttributes", "writeFileDescriptor", "getClassLoader", "createClassLoader",
                        "sun.rmi.runtime.RuntimeUtil.getInstance", "reflectionFactoryAccess", "accessClassInPackage.sun.reflect",
                        "accessDeclaredMembers", "fileSystemProvider", "getProtectionDomain",
                        "accessClassInPackage.sun.util.resources", "accessClassInPackage.sun.instrument", "accessClassInPackage.sun.management", "accessClassInPackage.sun.management.resources",
                        "accessClassInPackage.sun.util.logging.resources", "accessClassInPackage.sun.text.resources", "accessClassInPackage.com.sun.jmx.remote.internal",
                        "sun.misc.Perf.getPerf", "accessClassInPackage.sun.security.provider", "accessClassInPackage.com.sun.jmx.remote.protocol.jmxmp"
                        );
            }
            public void checkPermission(Permission perm) {
                final String permName = perm.getName();
                final String permAction = perm.getActions();
                final String permClass = perm.getClass().getName();
                if("java.io.FilePermission".equals(permClass)) {
                    if("read".equals(permAction)  && (permName.startsWith("/proc/") || permName.startsWith("/sys/") || permName.endsWith("jmxremote_optional.jar")))
                        return;
                }
                else if("java.net.SocketPermission".equals(permClass)) {
                    if(! "connect".equals(permAction))
                        return;
                }
                else if("java.net.NetPermission".equals(permClass)) {
                    return;
                }
                else if("java.util.PropertyPermission".equals(permClass) ) {
                    return;
                }
                else if("javax.management.MBeanServerPermission".equals(permClass) ) {
                    return;
                }
                else if("javax.management.MBeanPermission".equals(permClass) ) {
                    return;
                }
                else if("java.lang.management.ManagementPermission".equals(permClass) ) {
                    return;
                }
                else if("java.lang.RuntimePermission".equals(permClass)) {
                    if(runtimePermissions.contains(permName))
                        return;
                }
                else if("java.io.SerializablePermission".equals(permClass)) {
                    return;
                }
                else if("java.lang.reflect.ReflectPermission".equals(permClass)) {
                    return;
                }
                else if("java.util.logging.LoggingPermission".equals(permClass)) {
                    return;
                }
                else if("java.security.SecurityPermission".equals(permClass)) {
                    if ("getPolicy".equals(permName))
                        return;
                    else if ("getProperty.networkaddress.cache.ttl".equals(permName))
                        return;
                    else if ("getProperty.networkaddress.cache.negative.ttl".equals(permName))
                        return;
                    else if (permName.startsWith("getProperty.security.provider"))
                        return;
                    else if ("getProperty.securerandom.source".equals(permName))
                        return;
                    else if ("putProviderProperty.SUN".equals(permName))
                        return;
                }
                super.checkPermission(perm);
            }
            public void checkAccept(String host, int port) {}
            public void checkAccess(Thread t) {}
            public void checkAccess(ThreadGroup g) {}
            public void checkConnect(String host, int port, Object context) {}
            public void checkConnect(String host, int port) {}
            public void checkRead(FileDescriptor fd) {}
            public void checkRead(String file, Object context) {}
            public void checkRead(String file) {}
            public void checkLink(String lib) {}
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
        if (System.getSecurityManager() == null)
            System.setSecurityManager ( getSecurityManager() );
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
