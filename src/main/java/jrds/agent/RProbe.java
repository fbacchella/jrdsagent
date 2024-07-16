package jrds.agent;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RProbe extends Remote {
    String NAME = "dispatcher";

    Map<String, Number> query(String name) throws RemoteException, InvocationTargetException;
    String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException, InvocationTargetException;
    long getUptime() throws RemoteException, InvocationTargetException;
    Map<String, Map<String, Number>> batch(List<String> names) throws RemoteException, InvocationTargetException;
}
