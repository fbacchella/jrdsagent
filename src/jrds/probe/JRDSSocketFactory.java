package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.apache.log4j.Level;

import jrds.starter.SocketFactory;
import jrds.starter.Starter;

/**
 * @author Fabrice Bacchella
 * A simplified default RMI.
 * It support only a direct connection, it uses the default timeout from jrds
 */
public class JRDSSocketFactory extends Starter implements RMIClientSocketFactory {

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
