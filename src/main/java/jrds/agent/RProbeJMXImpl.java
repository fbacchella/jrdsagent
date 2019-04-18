package jrds.agent;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.JMX;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

public class RProbeJMXImpl extends StandardMBean implements RProbe {

    public static final String NAME = "jrds:type=agent";
    protected final RProbeActor actor;
    public static final void register(RProbeActor rp) throws InvocationTargetException {
        registerinstance(new RProbeJMXImpl(rp));
    }

    protected static void registerinstance(RProbeJMXImpl instance) throws InvocationTargetException {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(NAME);
            mbs.registerMBean(instance, name);
            // An empty call, to ensure initialization
            RProbe proxy = JMX.newMBeanProxy(mbs, name, RProbe.class);
            proxy.getUptime();
            
            Map<String, String> emptyMap = Collections.emptyMap();
            String probeName = proxy.prepare("jrds.agent.jmx.SystemInfo", emptyMap, Collections.emptyList());
            proxy.query(probeName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | NotCompliantMBeanException | MBeanException | RemoteException e) {
            throw new InvocationTargetException(e, "Error registring JMX");
        }
    }

    protected RProbeJMXImpl(RProbeActor actor) {
        super(jrds.agent.RProbe.class, false);
        this.actor = actor;
    }

    @Override
    public Map<String, Number> query(String name) throws RemoteException {
        try {
            return actor.query(name);
        } catch (Exception e) {
            throw new RemoteException("Error while quering " + name, e);
        }
    }

    /**
     * @param name
     * @param args
     * @return
     * @throws RemoteException
     * @see jrds.agent.RProbe#prepare(String, Map, List)
     */
    public String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException {
        try {
            return actor.prepare(name, specifics, args);
        } catch (InvocationTargetException e) {
            throw new RemoteException("Error while preparing " + name, e.getCause());
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
