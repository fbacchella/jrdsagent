package jrds.agent;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RProbe extends Remote {
    static String NAME = "dispatcher";

    public Map<String, Number> query(String name) throws RemoteException;
    public String prepare(String name, List<?> args) throws RemoteException, InvocationTargetException;
    public String prepare(String name, String statFile, List<?> args) throws RemoteException, InvocationTargetException;
    public long getUptime() throws RemoteException;
}
