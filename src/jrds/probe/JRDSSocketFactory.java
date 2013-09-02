package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.util.logging.Handler;

import jrds.JrdsLoggerConfiguration;
import jrds.JuliToLog4jHandler;
import jrds.starter.SocketFactory;
import jrds.starter.Starter;

import org.apache.log4j.Level;

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

}
