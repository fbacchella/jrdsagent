package jrds.probe;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.management.MalformedObjectNameException;
import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;

import org.apache.log4j.Level;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.exception.J4pRemoteException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pRequest;
import org.jolokia.client.request.J4pResponse;

import jrds.PropertiesManager;
import jrds.agent.RProbe;

public class JolokiaConnection extends AgentConnection {

    private J4pClient j4pClient;

    public RProbe getRemoteProbe() {
        return new RProbe() {

            @Override
            public Map<String, Number> query(String name) throws RemoteException, InvocationTargetException {
                try {
                    J4pExecRequest req = new J4pExecRequest("jrds:type=agent", "query", name);
                    J4pExecResponse resp = doRequest(req, name);
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            }

            @Override
            public String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException, InvocationTargetException {
                try {
                    J4pExecRequest req = new J4pExecRequest("jrds:type=agent", "prepare", name, specifics, args);
                    req.setPreferredHttpMethod("POST");
                    J4pExecResponse resp = doRequest(req, name);
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            }

            @Override
            public long getUptime() throws RemoteException, InvocationTargetException {
                try {
                    J4pReadRequest req = new J4pReadRequest("jrds:type=agent", "Uptime");
                    J4pReadResponse resp = doRequest(req, "uptime");
                    return (Long) resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            }

            private <RESP extends J4pResponse<REQ>, REQ extends J4pRequest> RESP doRequest(REQ req, String name) throws RemoteException, InvocationTargetException {
                try {
                    return j4pClient.execute(req);
                } catch (J4pRemoteException e) {
                    // Jolokia don't return the real exception message
                    // It needs to be extracted from the returned message
                    String fullmsg = e.getMessage();
                    String[] msgSplited = fullmsg.split("(;|\\n\\t)");
                    String message = msgSplited[msgSplited.length - 1].trim();
                    if ("jrds.agent.RProbeJolokiaImpl$RemoteNamingException".equals(e.getErrorType())) {
                        NameNotFoundException nnfe = new NameNotFoundException();
                        try {
                            nnfe.setRemainingName(new CompositeName(name));
                        } catch (InvalidNameException e1) {
                        }
                        throw new RemoteException(message, nnfe);
                    } else {
                        throw new RemoteException(message);
                    }
                } catch (J4pException e) {
                    if (e.getCause() != null) {
                        throw new InvocationTargetException(e.getCause());
                    } else {
                        throw new InvocationTargetException(e);
                    }
                }
            }
        };
    }

    @Override
    public boolean startConnection() {
        HttpClientStarter httpstarter = getLevel().find(HttpClientStarter.class);
        if (!httpstarter.isStarted()) {
            return false;
        }
        try {
            URL url = new URL("http", getHostName(), port, "/jolokia/");
            j4pClient = new J4pClient(url.toString(), httpstarter.getHttpClient());
            return super.startConnection();
        } catch (MalformedURLException e) {
            log(Level.ERROR, e, "can't build jolokia URL: %s", e.getMessage());
            return false;
        }
    }

    @Override
    public void stopConnection() {
        j4pClient = null;
        super.stopConnection();
    }

    @Override
    public void configure(PropertiesManager pm) {
        getLevel().getParent().registerStarter(new HttpClientStarter());
        super.configure(pm);
    }

}
