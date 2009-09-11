package jrds.probe;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import jrds.starter.SocketFactory;

/**
 * @author bacchell
 * A simplified default RMI.
 * It support only a directe connection, it uses the default timeout from jrds
 */
public class JRDSSocketFactory implements RMIClientSocketFactory {
	final SocketFactory sf;

	public JRDSSocketFactory(SocketFactory sf) {
		this.sf = sf;
	}

	public Socket createSocket(String host, int port) throws IOException {
		return sf.createSocket(host, port);
	}
}
