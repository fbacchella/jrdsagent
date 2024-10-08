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

import org.apache.http.conn.HttpHostConnectException;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.exception.J4pRemoteException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pExecResponse;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pRequest;
import org.jolokia.client.request.J4pResponse;
import org.jolokia.client.request.J4pVersionRequest;
import org.jolokia.client.request.J4pVersionResponse;
import org.slf4j.event.Level;

import jrds.PropertiesManager;
import jrds.agent.RProbe;

public class JolokiaConnection extends AgentConnection {

    private class JolokiaRprobe implements RProbe {
        @Override
        public Map<String, Number> query(String name) throws RemoteException, InvocationTargetException {
            if (isStarted()) {
                try {
                    J4pExecRequest req = new J4pExecRequest("jrds:type=agent", "query", name);
                    J4pExecResponse resp = doRequest(req, name);
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            } else {
                return Map.of();
            }
        }

        @Override
        public String prepare(String name, Map<String, String> specifics, List<?> args) throws RemoteException, InvocationTargetException {
            if (isStarted()) {
                try {
                    J4pExecRequest req = new J4pExecRequest("jrds:type=agent", "prepare", name, specifics, args);
                    req.setPreferredHttpMethod("POST");
                    J4pExecResponse resp = doRequest(req, name);
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            } else {
                return null;
            }
        }

        @Override
        public long getUptime() throws RemoteException, InvocationTargetException {
            if (isStarted()) {
                try {
                    J4pReadRequest req = new J4pReadRequest("jrds:type=agent", "Uptime");
                    J4pReadResponse resp = doRequest(req, "uptime");
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            } else {
                return -1;
            }
        }

        @Override
        public Map<String, Map<String, Number>> batch(List<String> queryNames)
                throws RemoteException, InvocationTargetException {
            if (isStarted()) {
                try {
                    J4pExecRequest req = new J4pExecRequest("jrds:type=agent", "batch", queryNames);
                    req.setPreferredHttpMethod("POST");
                    J4pExecResponse resp = doRequest(req, "batch");
                    return resp.getValue();
                } catch (MalformedObjectNameException e) {
                    throw new InvocationTargetException(e);
                }
            } else {
                return Map.of();
            }
        }

        protected  <RESP extends J4pResponse<REQ>, REQ extends J4pRequest> RESP doRequest(REQ req, String name) throws RemoteException, InvocationTargetException {
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
                        // never thrown
                    }
                    throw new RemoteException(message, nnfe);
                } else {
                    throw new RemoteException(message);
                }
            } catch (J4pException e) {
                if (e.getCause() != null && e.getCause() instanceof HttpHostConnectException) {
                    HttpHostConnectException cause = (HttpHostConnectException) e.getCause();
                    throw new InvocationTargetException(cause.getCause());
                } else if (e.getCause() != null) {
                    throw new InvocationTargetException(e.getCause());
                } else {
                    throw new InvocationTargetException(e);
                }
            }
        }

    }

    private J4pClient j4pClient;

    private final JolokiaRprobe remoteProbe = new JolokiaRprobe();

    public RProbe getRemoteProbe() {
        return remoteProbe;
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
            J4pVersionResponse version = remoteProbe.doRequest(new J4pVersionRequest(), null);
            log(Level.DEBUG, "Connected: %s", version.getAgentVersion());
            return super.startConnection();
        } catch (MalformedURLException e) {
            log(Level.ERROR, e, "Can't build jolokia URL: %s", e);
            return false;
        } catch (RemoteException | InvocationTargetException e) {
            log(Level.ERROR, e, "Failed to connect to remote agent: %s", e.getCause());
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
