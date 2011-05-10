package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import jrds.starter.SocketFactory;
import jrds.starter.Starter;
import jrds.starter.StarterNode;

/**
 * @author bacchell
 * A simplified default RMI.
 * It support only a directe connection, it uses the default timeout from jrds
 */
public class JRDSSocketFactory extends Starter implements RMIClientSocketFactory {

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
}
