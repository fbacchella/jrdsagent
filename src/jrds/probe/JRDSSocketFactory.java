package jrds.probe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.rmi.server.RMISocketFactory;

import jrds.HostsList;

/**
 * @author bacchell
 * A simplified default RMI.
 * It support only a directe connection, it uses the default timeout from jrds
 */
public class JRDSSocketFactory extends RMISocketFactory {
	public ServerSocket createServerSocket(int port) throws IOException {
		final int timeout = HostsList.getRootGroup().getTimeout();;
		ServerSocket s = new ServerSocket(port) {

			/* (non-Javadoc)
			 * @see java.net.ServerSocket#accept()
			 */
			@Override
			public Socket accept() throws IOException {
				Socket accepted = super.accept();
				accepted.setTcpNoDelay(true);
				return accepted;
			}
			
		};
		s.setSoTimeout(timeout * 1000);
		return s;
	}

	public Socket createSocket(String host, int port) throws IOException {
		final int timeout = HostsList.getRootGroup().getTimeout();;
		Socket s = new Socket(host, port) {
			public void connect(SocketAddress endpoint) throws IOException {
				super.connect(endpoint, timeout * 1000);
			}

			/* (non-Javadoc)
			 * @see java.net.Socket#connect(java.net.SocketAddress, int)
			 */
			public void connect(SocketAddress endpoint, int timeout) throws IOException {
				super.connect(endpoint, timeout);
			}
		};
		s.setSoTimeout(timeout * 1000);
		s.setTcpNoDelay(true);
		return s;
	}

}
