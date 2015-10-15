package jrds.agent;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;


public class RProbeJMXImpl extends StandardMBean implements jrds.agent.RProbe {
    public final static String NAME = "jrds:type=agent";
    private final RProbeActor actor;
    static public final void register(RProbeActor rp) throws InvocationTargetException {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(NAME);
            mbs.registerMBean(new RProbeJMXImpl(rp), name); 
        } catch (MalformedObjectNameException e) {
            throw new InvocationTargetException(e, "Error registring JMX");
        } catch (InstanceAlreadyExistsException e) {
            throw new InvocationTargetException(e, "Error registring JMX");
        } catch (MBeanRegistrationException e) {
            throw new InvocationTargetException(e, "Error registring JMX");
        } catch (NotCompliantMBeanException e) {
            throw new InvocationTargetException(e, "Error registring JMX");
        }
    }

    private RProbeJMXImpl(RProbeActor rp) {
        super(jrds.agent.RProbe.class, false);
        this.actor = rp;
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
