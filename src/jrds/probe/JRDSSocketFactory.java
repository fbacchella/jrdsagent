package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.util.logging.Handler;

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
        // formating and filtering is delegated to the log4j level
        JrdsLoggerConfiguration.configureLogger("sun.rmi", Level.ERROR);
        Handler jrdsLogger = new JuliToLog4jHandler();
        jrdsLogger.setLevel(java.util.logging.Level.ALL);
        java.util.logging.Logger.getLogger("sun.rmi").addHandler(jrdsLogger);
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
        log(Level.DEBUG, "creating a socket to %s:%d", host, port);
        return getLevel().find(SocketFactory.class).createSocket(host, port);
    }

    /* (non-Javadoc)
     * @see jrds.starter.Starter#isStarted()
     */
    @Override
    public boolean isStarted() {
        return true;
    }
    
    /**
     * The socket factory is identified by it's level,
     * it's state full, it can be running or stopped.
     */
    public boolean equals(Object o) {
        return (getClass() == o.getClass() &&
                getLevel() == ((JRDSSocketFactory) o).getLevel());
    }
    
    public int hashCode() {
         return getLevel().hashCode();
    }

}
