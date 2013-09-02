package jrds.probe;

import jrds.PropertiesManager;
import jrds.agent.RProbe;
import jrds.agent.RProbeJMXImpl;
import jrds.factories.ConnectionName;
import jrds.factories.ProbeBean;
import jrds.starter.Connection;

@ProbeBean({"port", "protocol"})
@ConnectionName(AgentConnection.CONNECTIONNAME)
public class AgentConnection extends Connection<RProbe> {
    final static String CONNECTIONNAME = "jrds.agent.AgentConnection";
    public static enum PROTOCOL {
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
        };
        abstract RProbe getRemoteProbe(Connection<?>proxy);
        abstract void configure(AgentConnection cnx, PropertiesManager pm);
        abstract Connection<?> getProxy();
    };

    private static final int AGENTPORT = 2002;
    private int port;
    private PROTOCOL protocol;
    private Connection<?> proxy = null;

    public AgentConnection() {
        super();
        this.port = AGENTPORT;
    }

    public AgentConnection(Integer port) {
        super();
        this.port = port;
    }

    @Override
    public RProbe getConnection() {
        return protocol.getRemoteProbe(proxy);
    }

    @Override
    public long setUptime() {
        return 0;
    }

    /* (non-Javadoc)
     * @see jrds.starter.Connection#getUptime()
     */
    @Override
    public long getUptime() {
        if(this.getClass() == AgentConnection.class)
            return proxy.getUptime();
        else
            return super.getUptime();
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
        this.protocol = PROTOCOL.valueOf(protocol.trim().toLowerCase());
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
            proxy.setName(proxy.getClass().getCanonicalName());
            getLevel().registerStarter(proxy);
            protocol.configure(this, pm);
            proxy.configure(pm);            
        }
    }

    @Override
    //Nothing to do, the proxy starter is registered, so it start by himself
    public boolean startConnection() {
        return true;
    }

    @Override
    public void stopConnection() {
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#isStarted()
     */
    @Override
    public boolean isStarted() {
        // Only the base class uses the proxy
        // The comptability sub class RMIConnection should not do that
        if(getClass() == AgentConnection.class) {
            if(proxy == null)
                return false;
            return proxy.isStarted();
        }
        else {
            return super.isStarted();
        }
    }

}
