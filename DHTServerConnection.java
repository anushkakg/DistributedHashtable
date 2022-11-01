import java.io.IOException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

class DHTServerConnection extends Thread {
        private Socket socket;
		private DHTServerNode dhtServerNode;

        public DHTServerConnection(DHTServerNode dhtServerNode, Socket socket) {
            this.socket = socket;
			this.dhtServerNode = dhtServerNode;
        }
        public void run() {
        	try {
        		//read from socket to ObjectInputStream object
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        		//create ObjectOutputStream object
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        		while (true) {
                	//convert ObjectInputStream object to String
                	Object object = inputStream.readObject();
                    if (object.getClass() == MsgGetRequest.class) {
						MsgGetRequest msgGetRequest = (MsgGetRequest) object;
						String value = dhtServerNode.getLocal(msgGetRequest.key);
						MsgGetResponse msgGetResponse = new MsgGetResponse(value);
						outputStream.writeObject(msgGetResponse);
                    } else if (object.getClass() == MsgPutRequest.class) {
						MsgPutRequest msgPutRequest = (MsgPutRequest) object;
						dhtServerNode.putLocal(msgPutRequest.key, msgPutRequest.value);
						MsgPutResponse msgPutResponse = new MsgPutResponse();
						outputStream.writeObject(msgPutResponse);
                    } else if (object.getClass() == MsgExitRequest.class) {
                        System.out.println("shutting down socket");
                    	break;
                    } else {
						System.out.println("invalid message on the socket");
						break;
					}
            	}
            	//close resources
                inputStream.close();
                outputStream.close();
            	socket.close();
        	} 
        	catch (IOException | ClassNotFoundException ex) {
        		System.out.println(ex.toString());
        	}
        }
    }