import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DHTServerListener extends Thread {
    private DHTServerNode dhtServerNode;
    private ServerSocket serverSocket;
	private boolean alive;

	public DHTServerListener (DHTServerNode dhtServerNode) {
		this.dhtServerNode = dhtServerNode;
		this.alive = true;
		InetSocketAddress localAddress = dhtServerNode.getAddress();
		int port = localAddress.getPort();

		//open server/listener socket
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException("\nCannot open listener port " + port+". Now exit.\n", e);
		}
	}

	@Override
	public void run() {
		while (alive) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				throw new RuntimeException(
						"Cannot accepting connection", e);
			}

			//new talker
			new Thread(new DHTServerConnection(dhtServerNode, socket)).start();
		}
	}

	public void toDie() {
		alive = false;
	}
}