package jrds.agent;

import java.io.File;
import java.io.FilePermission;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Permission;
import java.security.Permissions;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.Start.PROTOCOL;

public class AgentSecurityManager extends SecurityManager {

    private static final class PrivilegHolder  {
        private boolean privileged = false;
    }
    private static final ThreadLocal<PrivilegHolder> Privilege = ThreadLocal.withInitial(PrivilegHolder::new);

    private final Set<String> permUsed;
    private final Set<String> permCreated;

    private final ThreadLocal<Matcher> procinfoMatcher;
    private final ThreadLocal<Matcher> diskMatcher;

    private final boolean debugPerm;
    private final Permissions allowed = new Permissions();
    private final Set<String> filesallowed = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public AgentSecurityManager(boolean debugPerm, PROTOCOL proto) {
        this.debugPerm = debugPerm;

        Pattern procinfoPattern =  Pattern.compile("/proc/\\d+(/(cmdline|io|stat|statm|smaps|exe))?");
        procinfoMatcher = ThreadLocal.withInitial(() -> procinfoPattern.matcher(""));

        Pattern diskPattern =  Pattern.compile("/dev/((sd|hd|xvd|vd)[a-z]+\\d*|cciss/c\\dd\\d(p\\d+)?|nvme\\d+n\\d+(p\\d+)?|disk/by-[a-z]+/.*|mapper/.*|dm-\\d+|[0-9a-z]{33})");
        diskMatcher = ThreadLocal.withInitial(() -> diskPattern.matcher(""));

        if(debugPerm) {
            permUsed = Collections.newSetFromMap(new ConcurrentHashMap<>());
            permCreated = Collections.newSetFromMap(new ConcurrentHashMap<>());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                for(String i: new HashSet<>(permCreated)) {
                    if(permUsed.contains(i + " =")) {
                        permCreated.remove(i);
                    }
                }
                for(String i: permCreated){
                    permUsed.add(i +" +");
                }

                for(String p: new TreeSet<>(permUsed)) {
                    System.out.println(p);
                }
            }));
        } else {
            permUsed = null;
            permCreated = null;
        }

        //Add the java home to readable files
        Permission newPerm = new FilePermission(System.getProperty("java.home") + File.separator + "-", "read");
        allowed.add(newPerm);
        if(debugPerm) {
            permCreated.add(newPerm.toString());
        }

        Map<String, Set<Permission>> permsSets = getPermsSets();

        for(Permission i: permsSets.get("common")) {
            allowed.add(i);
            if(debugPerm) {
                permCreated.add(i.toString());
            }
        }

        for(Permission i: permsSets.get("forprobes")) {
            allowed.add(i);
            if(debugPerm) {
                permCreated.add(i.toString());
            }
        }

        for(Permission i: permsSets.get(proto.name())) {
            allowed.add(i);
            if(debugPerm) {
                permCreated.add(i.toString());
            }
        }
        if (proto == PROTOCOL.jolokia) {
            String jolokiaPermName = String.format("org.jolokia.service.history.History#updateAndAdd[jolokia:agent=%s,type=History]",
                    System.getProperty(RProbeJolokiaImpl.JOLOKIA_AGENT_ID)
                    );
            Permission jolokiaNewPerm = new javax.management.MBeanPermission(jolokiaPermName, "invoke");
            allowed.add(jolokiaNewPerm);
            if(debugPerm) {
                permCreated.add(jolokiaNewPerm.toString());
            }
        }

        allowed.setReadOnly();

    }

    @Override
    public void checkPermission(Permission perm) {
        // They can be an undefined number of security.provider.*
        // Used by jmxmp
        if(perm instanceof java.security.SecurityPermission
                        && perm.getName().startsWith("getProperty.security.provider") ) {
            if(debugPerm) {
                permUsed.add(perm + " =");
            }
            return;
        }
        if(perm instanceof java.io.FilePermission
                        && "read".equals(perm.getActions()) ) {
            String name = perm.getName();
            if(filesallowed.contains(name)) {
                if(debugPerm) {
                    permUsed.add(perm + " =");
                }
                return;
            }
            // Already allowed, don't check anymore
            if(allowed.implies(perm)) {
                if(debugPerm) {
                    permUsed.add(perm + " =");
                }
                filesallowed.add(name);
                return;
            }

            // Perhaps it's in the allowed /proc/<pid>/... files
            procinfoMatcher.get().reset(name);
            if (procinfoMatcher.get().matches() && "read".equals(perm.getActions())) {
                if(debugPerm) {
                    permUsed.add("(\"java.io.FilePermission\" \"" + procinfoMatcher.get().pattern().pattern() + "\" \"read\") =");
                }
                return;
            }
            procinfoMatcher.get().reset("");

            // Or it's block device
            diskMatcher.get().reset(name);
            if (diskMatcher.get().matches() && "read".equals(perm.getActions())) {
                if(debugPerm) {
                    permUsed.add("(\"java.io.FilePermission\" \"" + diskMatcher.get().pattern().pattern() + "\" \"read\") =");
                }
                filesallowed.add(name);
                return;
            }
            diskMatcher.get().reset("");

            // Only non-hidden folder are allowed, for file system usage
            // If it calls itself, privileges will be set to true,
            // so it can check isDirectory and isHidden
            PrivilegHolder privileged = Privilege.get();
            if(privileged.privileged) {
                return;
            } else {
                File fullpath = new File(name);
                privileged.privileged = true;
                boolean allowed;
                try {
                    allowed = fullpath.isDirectory() && ! fullpath.isHidden();
                } finally {
                    privileged.privileged = false;
                }
                if(allowed) {
                    if(debugPerm) {
                        permUsed.add(perm + " =");
                    }
                    filesallowed.add(name);
                    return;
                }
            }
        }
        if (allowed.implies(perm)) {
            if(debugPerm) {
                permUsed.add(perm.toString() + " =");
            }
            return;
        }
        try {
            super.checkPermission(perm);
            // Was accepted, but we should add it anyway
            // Some accepted permissions make jrdsagent failed anyway
            if(debugPerm) {
                permUsed.add(perm.toString() + " ?");
            }
        } catch (SecurityException e) {
            if(debugPerm) {
                permUsed.add(perm.toString() + " -");
            } else {
                throw new CollectException("Missing permission: " + perm.toString(), e);
            }
        }
    }

    private Map<String, Set<Permission>> getPermsSets() {
        Map<String, String[][]> permsDescription = new HashMap<>();
        Map<String, Set<Permission>> permsSets = new HashMap<>();

        permsDescription.put("common", new String[][] {
            new String[] { "java.lang.RuntimePermission", "accessDeclaredMembers" },
            new String[] { "java.lang.RuntimePermission", "createClassLoader" },
            new String[] { "java.lang.RuntimePermission", "fileSystemProvider" },
            new String[] { "java.lang.RuntimePermission", "getClassLoader" },
            new String[] { "java.lang.RuntimePermission", "getFileSystemAttributes" },
            new String[] { "java.lang.RuntimePermission", "getProtectionDomain" },
            new String[] { "java.lang.RuntimePermission", "loadLibrary.net" }, // Needed on windows
            new String[] { "java.lang.RuntimePermission", "modifyThread" },
            new String[] { "java.lang.RuntimePermission", "modifyThreadGroup" },
            new String[] { "java.lang.RuntimePermission", "readFileDescriptor" },
            new String[] { "java.lang.RuntimePermission", "setContextClassLoader" },
            new String[] { "java.lang.RuntimePermission", "writeFileDescriptor" },
            // Welcome java's modules
            new String[] { "java.lang.RuntimePermission", "accessSystemModules" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.jdk.internal.reflect" },
            new String[] { "java.lang.reflect.ReflectPermission", "suppressAccessChecks" },
            new String[] { "java.net.NetPermission", "specifyStreamHandler" }, // Appears with jar in extensions directory
            new String[] { "java.net.NetPermission", "setSocketImpl" }, // Since Java 14
            new String[] { "java.util.logging.LoggingPermission", "control", "" },
            new String[] { "java.util.PropertyPermission", "java.nio.file.spi.DefaultFileSystemProvider", "read" }, 
            new String[] { "java.util.PropertyPermission", "jdk.logging.allowStackWalkSearch", "read" }, 
            new String[] { "java.util.PropertyPermission", "jdk.net.ephemeralPortRange.high", "read" },  // Needed on windows
            new String[] { "java.util.PropertyPermission", "jdk.net.ephemeralPortRange.low", "read" },   // Needed on windows
            new String[] { "java.util.PropertyPermission", "sun.jnu.encoding", "read" }, 
            new String[] { "java.util.PropertyPermission", "sun.nio.fs.chdirAllowed", "read" },
        });
        permsDescription.put("forprobes", new String[][] {
            new String[] { "java.io.FilePermission", "/proc", "read" },
            new String[] { "java.io.FilePermission", "/proc/*", "read" },
            new String[] { "java.io.FilePermission", "/proc/net/*", "read" },
            new String[] { "java.io.FilePermission", "/proc/net/rpc/*", "read" },
            new String[] { "java.io.FilePermission", "/sys/devices/system/node/-", "read" },
            new String[] { "java.io.FilePermission", "/dev/mapper/-", "read" },
            new String[] { "java.io.FilePermission", "/dev/disk/-", "read" },
        });
        permsDescription.put(PROTOCOL.rmi.name(), new String[][] {
            new String[] { "java.io.SerializablePermission", "enableSubstitution" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
            new String[] { "java.lang.RuntimePermission", "getProtectionDomain" },
            new String[] { "java.lang.RuntimePermission", "loadLibrary.rmi" },     // Needed on jdk 6
            new String[] { "java.lang.RuntimePermission", "reflectionFactoryAccess" },
            new String[] { "java.lang.RuntimePermission", "sun.rmi.runtime.RuntimeUtil.getInstance" },
            new String[] { "java.net.SocketPermission", "*", "accept,resolve" },
            new String[] { "java.security.SecurityPermission", "getPolicy" },
            new String[] { "java.util.PropertyPermission", "java.rmi.server.RMIClassLoaderSpi", "read" },
            new String[] { "java.util.PropertyPermission", "java.rmi.server.codebase", "read" },
            new String[] { "java.util.PropertyPermission", "java.rmi.server.useCodebaseOnly", "read" },
            new String[] { "java.util.PropertyPermission", "jdk.internal.lambda.dumpProxyClasses", "read" },
            new String[] { "java.util.PropertyPermission", "sun.io.serialization.extendedDebugInfo", "read" },
            new String[] { "java.util.PropertyPermission", "sun.net.maxDatagramSockets", "read" },
            new String[] { "java.util.PropertyPermission", "sun.rmi.dgc.ackTimeout", "read" },
            new String[] { "java.util.PropertyPermission", "sun.rmi.loader.logLevel", "read" },
            new String[] { "java.util.PropertyPermission", "sun.rmi.transport.connectionTimeout", "read" },
            new String[] { "java.util.PropertyPermission", "sun.rmi.transport.tcp.handshakeTimeout", "read" },
            new String[] { "java.util.PropertyPermission", "sun.rmi.transport.tcp.responseTimeout", "read" },
            new String[] { "java.util.PropertyPermission", "sun.util.logging.disableCallerCheck", "read" },
        });
        permsDescription.put(PROTOCOL.jmx.name(), new String[][] {
            new String[] { "java.io.SerializablePermission", "enableSubstitution" },
            new String[] { "java.io.SerializablePermission", "serialFilter" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect.misc" },
            new String[] { "java.net.NetPermission", "getProxySelector" },
            new String[] { "java.net.SocketPermission", "*", "accept,listen,resolve" },
            new String[] { "java.security.SecurityPermission", "getPolicy" },
            new String[] { "java.util.PropertyPermission", "java.rmi.server.hostname", "read" },
            new String[] { "java.util.PropertyPermission", "java.rmi.server.randomIDs", "read" },
            new String[] { "java.util.PropertyPermission", "socksProxyHost", "read" },
            new String[] { "java.util.PropertyPermission", "sun.net.maxDatagramSockets", "read" },
            new String[] { "javax.management.MBeanPermission", "-#-[-]", "getClassLoaderRepository" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#-[jrds:type=agent]", "getClassLoaderFor" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#prepare[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#query[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#Uptime[jrds:type=agent]", "getAttribute" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#batch[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#-[jrds:type=agent]", "getClassLoaderFor" },
            new String[] { "javax.management.MBeanPermission", "sun.management.RuntimeImpl#Uptime[java.lang:type=Runtime]", "getAttribute" },
        });
        permsDescription.put(PROTOCOL.jmxmp.name(), new String[][] {
            new String[] { "java.io.FilePermission", "/dev/random", "read" },
            new String[] { "java.io.FilePermission", "/dev/urandom", "read" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect.misc" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.security.provider" },
            new String[] { "java.net.SocketPermission", "*", "accept,resolve" },
            new String[] { "java.security.SecurityPermission", "getProperty.securerandom.source" },
            new String[] { "java.security.SecurityPermission", "putProviderProperty.SUN" },
            new String[] { "java.util.PropertyPermission", "com.sun.jmx.remote.bug.compatible", "read" },
            new String[] { "java.util.PropertyPermission", "java.security.egd", "read" },
            new String[] { "java.util.PropertyPermission", "os.arch", "read" },
            new String[] { "java.util.PropertyPermission", "sun.net.maxDatagramSockets", "read" },
            new String[] { "java.util.PropertyPermission", "sun.util.logging.disableCallerCheck", "read" },
            new String[] { "javax.management.MBeanPermission", "-#-[-]", "getClassLoaderRepository" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#-[jrds:type=agent]", "getClassLoaderFor" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#prepare[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#query[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#batch[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJMXImpl#Uptime[jrds:type=agent]", "getAttribute" },
            new String[] { "javax.management.MBeanPermission", "sun.management.RuntimeImpl#Uptime[java.lang:type=Runtime]", "getAttribute" },
        });
        permsDescription.put(PROTOCOL.jolokia.name(), new String[][] {
            new String[] { "java.io.FilePermission", "/usr/share/javazi", "read" },
            new String[] { "java.io.FilePermission", "/usr/share/javazi/-", "read" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.reflect.misc" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.text.resources" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.text.resources.*" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.util.resources" },
            new String[] { "java.lang.RuntimePermission", "accessClassInPackage.sun.util.resources.*" },
            new String[] { "java.lang.RuntimePermission", "loadLibrary.nio" },
            new String[] { "java.lang.RuntimePermission", "enableContextClassLoaderOverride" },
            new String[] { "java.net.SocketPermission", "*", "accept,resolve" },
            new String[] { "java.util.PropertyPermission", "java.home", "read" },
            new String[] { "java.util.PropertyPermission", "java.util.concurrent.ForkJoinPool.common.exceptionHandler", "read" },
            new String[] { "java.util.PropertyPermission", "java.util.concurrent.ForkJoinPool.common.maximumSpare", "read" },
            new String[] { "java.util.PropertyPermission", "java.util.concurrent.ForkJoinPool.common.parallelism", "read" },
            new String[] { "java.util.PropertyPermission", "java.util.concurrent.ForkJoinPool.common.threadFactory", "read" },
            new String[] { "java.util.PropertyPermission", "java.util.currency.data", "read" },
            new String[] { "java.util.PropertyPermission", "jdk.logging.allowStackWalkSearch", "read" },
            new String[] { "java.util.PropertyPermission", "jdk.net.revealLocalAddress", "read" },
            new String[] { "java.util.PropertyPermission", "line.separator", "read" },
            new String[] { "java.util.PropertyPermission", "sun.timezone.ids.oldmapping", "read" },
            new String[] { "java.util.PropertyPermission", "user.country", "read" },
            new String[] { "java.util.PropertyPermission", "user.dir", "read" },
            new String[] { "java.util.PropertyPermission", "user.timezone", "read,write" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJolokiaImpl#-[jrds:type=agent]", "getMBeanInfo" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJolokiaImpl#Uptime[jrds:type=agent]", "getAttribute" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJolokiaImpl#prepare[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJolokiaImpl#query[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanPermission", "jrds.agent.RProbeJolokiaImpl#batch[jrds:type=agent]", "invoke" },
            new String[] { "javax.management.MBeanServerPermission", "createMBeanServer"},
        });
        Class<?>[][] typeVector = new Class<?>[][]{
            new Class<?>[] { String.class },
            new Class<?>[] { String.class, String.class },
        };
        for (Entry<String, String[][]> e: permsDescription.entrySet()) {
            String name = e.getKey();
            Set<Permission> current = new HashSet<>();
            permsSets.put(name, current);
            for (String[] a: e.getValue()) {
                String className = a[0];
                String[] argVector = Arrays.copyOfRange(a, 1, a.length);
                try {
                    Class<?> cl = Start.class.getClassLoader().loadClass(className);
                    Constructor<?> c = cl.getConstructor(typeVector[argVector.length - 1]);
                    Permission newPerm = (Permission) c.newInstance((Object[])argVector);
                    current.add(newPerm);
                } catch (InstantiationException | IllegalAccessException
                                | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
                    throw new IllegalArgumentException("Can't add permission " + className + "(" + argVector[0] + ")", ex);
                }
            }
        }
        //Adding some autobuild permissions
        for (String path: System.getProperty("java.endorsed.dirs", "").split(File.pathSeparator)) {
            Permission newPerm = new java.io.FilePermission(path + "/*", "read");
            permsSets.get("common").add(newPerm);
        }
        for (String path: System.getProperty("java.ext.dirs", "").split(File.pathSeparator)) {
            Permission newPerm = new java.io.FilePermission(path + "/*", "read");
            permsSets.get("common").add(newPerm);
        }
        return permsSets;
    }

}
