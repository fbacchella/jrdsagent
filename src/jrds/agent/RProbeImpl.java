package jrds.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RProbeImpl extends UnicastRemoteObject implements RProbe, Serializable {
    static final long serialVersionUID = -7914792289084645089L;

    final private Map<String,LProbe> probeMap = new HashMap<String,LProbe>();
    final private long startime = System.currentTimeMillis();
    final private String UPTIMEFILE = "/proc/uptime";

    public RProbeImpl() throws RemoteException {
        super();
    }

    public RProbeImpl(int port) throws RemoteException {
        super(port);
    }

    public Map<String,Number> query(String name) throws RemoteException {
        Map<String,Number> retValue = new HashMap<String,Number>(0);
        LProbe p =  probeMap.get(name);
        if(p != null)
            retValue = p.query();
        else
            throw new RemoteException("Remote probe " + name + " not found");
        return retValue;
    }

    private LProbe getLProbe(String name) throws RemoteException {
        try {
            Class<?> probeClass = Class.forName(name);
            Constructor<?> theConst = probeClass.getConstructor();
            return (LProbe) theConst.newInstance();
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + name, e);
        }
    }

    private Boolean configure(LProbe p, List<?> args ) throws RemoteException {
        try {
            Class<?>[] argsType = new Class[args.size()];
            Object[] argsVal = new Object[args.size()];
            int index = 0;
            for(Object thisarg: args) {
                argsType[index] = thisarg.getClass();
                argsVal[index] = thisarg;
            }
            Method m = p.getClass().getMethod("configure", argsType);
            Object result = m.invoke(p, argsVal);
            return(Boolean) result;
        } catch (Exception e) {
            throw new RemoteException("Error while preparing " + p.getName(), e);
        }
    }

    public String prepare(String name, List<?> args) throws RemoteException {
        return prepare(name, null, args);
    }

    public String prepare(String name, String statFile, List<?> args)
            throws RemoteException {
        LProbe p = getLProbe(name);
        if(statFile != null)
            p.setStatFile(new File(statFile));
        boolean configured = configure(p, args);
        if( ! configured)
            throw new RemoteException("configure failed for " + p.getName());

        String iname = p.getName();
        probeMap.put(iname, p);
        return iname;
    }

    public boolean exist(String name) throws RemoteException {
        return probeMap.containsKey(name);
    }

    public long getUptime() throws RemoteException {
        File uptimef = new File(UPTIMEFILE);
        //Put a default value, being the agent uptime
        long uptime = (System.currentTimeMillis() - startime)/1000;
        if(uptimef.canRead()) {
            try {
                BufferedReader r = new BufferedReader(new FileReader(uptimef));
                String uptimes[] = r.readLine().trim().split(" ");
                uptime = (long) Double.parseDouble(uptimes[0]);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (NumberFormatException e) {
            }

        }
        return uptime;
    }

}
