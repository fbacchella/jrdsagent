package jrds.probe;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.event.Level;

import jrds.PropertiesManager;
import jrds.agent.RProbe;
import jrds.agent.RProbeJMXImpl;
import jrds.factories.ConnectionName;
import jrds.factories.ProbeBean;
import jrds.starter.Connection;

@ProbeBean({"port", "protocol", "batchCollect"})
@ConnectionName(AgentConnection.CONNECTIONNAME)
public class AgentConnection extends Connection<RProbe> {
    static final String CONNECTIONNAME = "jrds.agent.AgentConnection";

    public enum PROTOCOL {
        rmi {
            @Override
            RProbe getRemoteProbe(Connection<?> proxy) {
                return ((RMIConnection) proxy).getConnection();
            }
            @Override
            void configure(AgentConnection cnx, PropertiesManager pm) {
                ((RMIConnection) cnx.proxy).setPort(cnx.getPort());
            }
            @Override
            Connection<?> getProxy() {
                return new RMIConnection();
            }
        },
        jmx {
            @Override
            RProbe getRemoteProbe(Connection<?> proxy) {
                return ((JMXConnection) proxy).getMBean(RProbeJMXImpl.NAME, RProbe.class);
            }
            @Override
            void configure(AgentConnection cnx, PropertiesManager pm) {
                ((JMXConnection) cnx.proxy).setPort(cnx.getPort());
                ((JMXConnection) cnx.proxy).setProtocol("rmi");
            }
            @Override
            Connection<?> getProxy() {
                return new JMXConnection();
            }
        },
        jmxmp {
            @Override
            RProbe getRemoteProbe(Connection<?> proxy) {
                return ((JMXConnection) proxy).getMBean(RProbeJMXImpl.NAME, RProbe.class);
            }
            @Override
            void configure(AgentConnection cnx, PropertiesManager pm) {
                ((JMXConnection) cnx.proxy).setPort(cnx.getPort());
                ((JMXConnection) cnx.proxy).setProtocol("jmxmp");
            }
            @Override
            Connection<?> getProxy() {
                return new JMXConnection();
            }
        },
        jolokia {
            @Override
            RProbe getRemoteProbe(Connection<?> proxy) {
                return ((JolokiaConnection) proxy).getRemoteProbe();
            }
            @Override
            void configure(AgentConnection cnx, PropertiesManager pm) {
                ((JolokiaConnection) cnx.proxy).setPort(cnx.getPort());
            }
            @Override
            Connection<?> getProxy() {
                return new JolokiaConnection();
            }
        },
        embedded {
            @Override
            RProbe getRemoteProbe(Connection<?> proxy) {
                return ((LocalAgentConnection) proxy).getConnection();
            }
            @Override
            void configure(AgentConnection cnx, PropertiesManager pm) {
            }
            @Override
            Connection<?> getProxy() {
                return new LocalAgentConnection();
            }
        };
        abstract RProbe getRemoteProbe(Connection<?> proxy);
        abstract void configure(AgentConnection cnx, PropertiesManager pm);
        abstract Connection<?> getProxy();
    }

    private static final int AGENTPORT = 2002;
    protected int port = AGENTPORT;
    private PROTOCOL protocol = PROTOCOL.rmi;

    private boolean batchCollect = false;
    private Connection<?> proxy = null;
    private long uptime = -1;
    private final Set<String> toBatch;
    private final Map<String, Map<String, Number>> batchedValues;

    public AgentConnection() {
        if (getClass() == AgentConnection.class) {
            toBatch = new HashSet<>();
            batchedValues = new HashMap<>();
        } else {
            toBatch = Set.of();
            batchedValues = Map.of();
        }
    }

    public AgentConnection(Integer port) {
        this();
        this.port = port;
    }

    @Override
    public RProbe getConnection() {
        return protocol.getRemoteProbe(proxy);
    }

    /**
     * This method return a dummy value, the real one
     * will be read using the proxy connection
     * and it needs it to be started
     * @see jrds.starter.Connection#setUptime()
     */
    @Override
    public long setUptime() {
        return -1;
    }

    /* (non-Javadoc)
     * @see jrds.starter.Connection#getUptime()
     */
    @Override
    public long getUptime() {
        if (uptime < 0 && isStarted()) {
            try {
                // Never used the connection directly
                // Needed to make the instance resolve it
                // because only AgentConnexion used it, not the sub classes
                uptime = getConnection().getUptime();
                log(Level.DEBUG, "uptime is %dms", uptime);
                batchCollect();
            } catch (RemoteException | InvocationTargetException e) {
                log(Level.ERROR, e, "uptime failed: %s", e.getCause());
                uptime = -1;
            }
        }
        return uptime;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol.name();
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = PROTOCOL.valueOf(protocol.trim().toLowerCase(Locale.ENGLISH));
    }

    public Boolean isBatchCollect() {
        return batchCollect;
    }

    public void setBatchCollect(Boolean batchCollect) {
        this.batchCollect = batchCollect;
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#configure(jrds.PropertiesManager)
     */
    @Override
    public void configure(PropertiesManager pm) {
        super.configure(pm);
        // Only the base class configures the proxy
        // The comptability sub class RMIConnection should not do that
        if(getClass() == AgentConnection.class) {
            proxy = protocol.getProxy();
            proxy.setName(proxy.getClass().getCanonicalName() + "@" + proxy.hashCode());
            getLevel().registerStarter(proxy);
            protocol.configure(this, pm);
            proxy.configure(pm);
        }
    }

    //Nothing to do, the proxy starter is registered, so it start by himself
    @Override
    public boolean startConnection() {
        uptime = -1;
        return true;
    }

    private void batchCollect() {
        if (batchCollect) {
            try {
                Map<String, Map<String, Number>> foundValues = protocol.getRemoteProbe(proxy).batch(new ArrayList<>(toBatch));
                batchedValues.putAll(foundValues);
            } catch (RemoteException | InvocationTargetException ex) {
                Throwable cause = Optional.ofNullable(ex.getCause()).orElse(ex);
                log(Level.ERROR, cause, "Failed batch: %s", cause.getMessage());
            }
        }
    }

    @Override
    public void stopConnection() {
        if (getClass() == AgentConnection.class && batchCollect) {
            batchedValues.clear();
        }
        uptime = -1;
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#isStarted()
     */
    @Override
    public boolean isStarted() {
        // Only the base class uses the proxy
        // The comptability sub class RMIConnection should not do that
        if (getClass() == AgentConnection.class) {
            return (proxy != null && proxy.isStarted()) && super.isStarted();
        } else {
            return super.isStarted();
        }
    }

    public void toBatch(String remoteName) {
        if (batchCollect && getClass() == AgentConnection.class) {
            toBatch.add(remoteName);
        }
    }

    public void unbatch(String remoteName) {
        if (batchCollect && getClass() == AgentConnection.class) {
            toBatch.remove(remoteName);
        }
    }

    public Optional<Map<String, Number>> getValuesFromBatch(String name) {
        if (batchCollect && batchedValues.containsKey(name)) {
            return Optional.of(batchedValues.get(name));
        } else {
            return Optional.empty();
        }
    }

}
