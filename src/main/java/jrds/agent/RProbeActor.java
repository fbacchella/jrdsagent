package jrds.agent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameNotFoundException;


public class RProbeActor {

    private static final Map<String,LProbe> probeMap = new HashMap<>();
    private final SystemUptime uptime;

    public RProbeActor() {
        String uptimeClassName = System.getProperty("jrds.uptimeClass", "");
        if(uptimeClassName.trim().isEmpty()) {
            String osname = System.getProperty("os.name");
            if("Linux".equals(osname)) {
                uptimeClassName = "jrds.agent.linux.LinuxSystemUptime";
            }
            else if(osname.startsWith("Windows")) {
                uptimeClassName = "jrds.agent.windows.pdh.WindowsSystemUptime";
            }
            else {
                uptimeClassName = "jrds.agent.jmx.JmxSystemUptime";
            }
        }
        try {
            @SuppressWarnings("unchecked")
            Class<SystemUptime> uptimeClass = (Class<SystemUptime>) RProbeActor.class.getClassLoader().loadClass(uptimeClassName.trim());
            uptime = uptimeClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                        IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Unable to find uptime class " + uptimeClassName + ": " + e.getMessage(), e);
        }
    }

    public Map<String,Number> query(String name) throws NameNotFoundException {
        LProbe p =  probeMap.get(name);
        if(p != null) {
            Map<String,Number> collected = p.query();
            // Remove NaN, it brake Jolokia
            Map<String,Number> retValue = new HashMap<>(collected.size());
            for (Map.Entry<String, Number> e: collected.entrySet()) {
                if (! Double.isNaN(e.getValue().doubleValue())) {
                    retValue.put(e.getKey(), e.getValue());
                }
            }
            return retValue;
        }
        else {
            NameNotFoundException e = new NameNotFoundException("'" + name + "' not founds, needs to prepare");
            Name cn = new CompositeName();
            try {
                cn.add(name);
            } catch (InvalidNameException ex) {
                throw new CollectException("Invalid collect name: " + name, ex);
            }
            e.setRemainingName(cn);
            throw e;
        }
    }

    private LProbe getLProbe(String name) throws InvocationTargetException {
        Class<?> probeClass;
        try {
            probeClass = Class.forName(name);
            Constructor<?> theConst = probeClass.getConstructor();
            return (LProbe) theConst.newInstance();
        } catch (ExceptionInInitializerError e) {
            throw new InvocationTargetException(e.getCause(), "Class " + name + " can't be initialized");
        } catch (NoClassDefFoundError | ClassNotFoundException e) {
            throw new InvocationTargetException(e, "Class " + name + " not found");
        } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        }
    }

    private Boolean configure(LProbe p, List<?> args ) throws InvocationTargetException {
        Class<?>[] argsType = new Class<?>[args.size()];
        Object[] argsVal = new Object[args.size()];
        int index = 0;
        for(Object thisarg: args) {
            if (thisarg == null) {
                throw new InvocationTargetException(new IllegalArgumentException("argument " + index + " not defined"), "Error configuring probe " + p);
            }
            argsType[index] = thisarg.getClass();
            argsVal[index] = thisarg;
            index++;
        }
        try {
            Method m = p.getClass().getMethod("configure", argsType);
            Object result = m.invoke(p, argsVal);
            return (Boolean) result;
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException e) {
            throw new InvocationTargetException(e, "Error configuring probe " + p);
        }
    }

    public String prepare(String name, Map<String, String> properties, List<?> args) throws InvocationTargetException {
        LProbe p = getLProbe(name);
        for(Map.Entry<String, String> e: properties.entrySet()) {
            p.setProperty(e.getKey(), e.getValue());
        }
        boolean configured = configure(p, args);
        if( ! configured)
            throw new CollectException("Configuration failed for " + p.getName());

        String iname = p.getName();
        probeMap.put(iname, p);
        return iname;
    }

    /**
     * Java don't provide an standard way to get system uptime
     * so it's actor dependant
     * @see jrds.agent.RProbe#getUptime()
     */
    public long getUptime() {
        return uptime.getSystemUptime();
    }

    Map<String, Map<String, Number>> batch(List<String> names) {
        Map<String, Map<String, Number>> values = new HashMap<>();

        for (String name: names) {
            try {
                Map<String, Number> probeValues = query(name);
                values.put(name, probeValues);
            } catch (NameNotFoundException ex) {
                // Ignore, will handle latter
            }
        }
        return values;
    }

}
