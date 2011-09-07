package jrds.agent;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class TestProbe extends LProbe {

    public String getName() throws RemoteException {
        return "TestProbe";
    }

    public Map<String,Number> query() throws RemoteException {
        Map<String,Number> m = new HashMap<String,Number>(1);
        m.put("time", new Double(System.currentTimeMillis()));
        return m;
    }

}
