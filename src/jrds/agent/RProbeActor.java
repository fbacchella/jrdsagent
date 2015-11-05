package jrds.agent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RProbeActor implements RProbe {

    final private Map<String,LProbe> probeMap = new HashMap<String,LProbe>();
    final private SystemUptime uptime;

    public RProbeActor() {
        String uptimeClassName = System.getProperty("jrds.uptimeClass", "");
        if(! uptimeClassName.trim().isEmpty()) {
        } else {
            String osname = System.getProperty("os.name");
            if("Linux".equals(osname)) {
                uptimeClassName = "jrds.agent.linux.LinuxSystemUptime";
            }
            else if(osname.startsWith("Windows")) {
                uptimeClassName = "jrds.agent.windows.WindowsSystemUptime";
            }
            else {
                uptimeClassName = "jrds.agent.jmx.JmxSystemUptime";
            }            
        }
        try {
            @SuppressWarnings("unchecked")
            Class<SystemUptime> uptimeClass = (Class<SystemUptime>) RProbeActor.class.getClassLoader().loadClass(uptimeClassName.trim());
            uptime = uptimeClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find uptime class " + uptimeClassName);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to find uptime class " + uptimeClassName);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to find uptime class " + uptimeClassName);
        }
    }

    public Map<String,Number> query(String name) {
        Map<String,Number> retValue = new HashMap<String,Number>(0);
        LProbe p =  probeMap.get(name);
        if(p != null)
            retValue = p.query();
        else
            throw new RuntimeException("Remote probe " + name + " not found");
        return retValue;
    }

    private LProbe getLProbe(String name) throws InvocationTargetException {
        Class<?> probeClass;
        try {
            probeClass = Class.forName(name);
            Constructor<?> theConst = probeClass.getConstructor();
            return (LProbe) theConst.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        } catch (IllegalArgumentException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        } catch (InstantiationException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        } catch (IllegalAccessException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        } catch (SecurityException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Error instanciating probe " + name);
        }
    }

    private Boolean configure(LProbe p, List<?> args ) throws InvocationTargetException {
        Class<?>[] argsType = new Class[args.size()];
        Object[] argsVal = new Object[args.size()];
        int index = 0;
        for(Object thisarg: args) {
            argsType[index] = thisarg.getClass();
            argsVal[index] = thisarg;
            index++;
        }
        try {
            Method m = p.getClass().getMethod("configure", argsType);
            Object result = m.invoke(p, argsVal);
            return(Boolean) result;
        } catch (SecurityException e) {
            throw new InvocationTargetException(e, "Error configuring probe " + p);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Error configuring probe " + p);
        } catch (IllegalArgumentException e) {
            throw new InvocationTargetException(e, "Error configuring probe " + p);
        } catch (IllegalAccessException e) {
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
            throw new RuntimeException("configure failed for " + p.getName());

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

}
