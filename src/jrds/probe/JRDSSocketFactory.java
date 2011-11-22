package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.apache.log4j.Level;

import jrds.JrdsLoggerConfiguration;
import jrds.JuliToLog4jHandler;
import jrds.starter.SocketFactory;
import jrds.starter.Starter;
import jrds.starter.StarterNode;

/**
 * @author bacchell
 * A simplified default RMI.
 * It support only a directe connection, it uses the default timeout from jrds
 */
public class JRDSSocketFactory extends Starter implements RMIClientSocketFactory {
    static
    {
        // java.util.logging reconfiguration
        // formating and filtering is delegated to the log4g level
        JrdsLoggerConfiguration.configureLogger("sun.rmi", Level.ERROR);
        java.util.logging.Logger.getLogger("sun.rmi").addHandler(new JuliToLog4jHandler());
        java.util.logging.Logger.getLogger("sun.rmi").setLevel(java.util.logging.Level.ALL);

    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#initialize(jrds.starter.StarterNode)
     */
    @Override
    public void initialize(StarterNode level) {
        super.initialize(level);
        getLevel().registerStarter(new SocketFactory());
    }

    public Socket createSocket(String host, int port) throws IOException {
        return getLevel().find(SocketFactory.class).createSocket(host, port);
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#isStarted()
     */
    @Override
    public boolean isStarted() {
        return true;
    }
}
