import java.util.*;
import java.io.*;
import java.net.*;

public class DHTServer {
    class ClientThread extends Thread {
        private Socket socket;
        private String name;
		private DHTServer dhtServer;
        public ClientThread(DHTServer dhtServer, Socket clientSocket, String clientName) {
            this.socket = clientSocket;
            this.name = clientName;
			this.dhtServer = dhtServer;
        }
        public void run() {
        	try {
        		//read from socket to ObjectInputStream object
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        		//create ObjectOutputStream object
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        		while (true) {
                	
                	//convert ObjectInputStream object to String
                	Object object = ois.readObject();
                    if (object.getClass() == MsgGetRequest.class) {
						MsgGetRequest msgGetRequest = (MsgGetRequest) object;
						String value = dhtServer.getHashtable().get(msgGetRequest.key);
						MsgGetResponse msgGetResponse = new MsgGetResponse(value);
						oos.writeObject(msgGetResponse);
                    } else if (object.getClass() == MsgPutRequest.class) {
						MsgPutRequest msgPutRequest = (MsgPutRequest) object;
						dhtServer.getHashtable().put(msgPutRequest.key, msgPutRequest.value);
						MsgPutResponse msgPutResponse = new MsgPutResponse();
						oos.writeObject(msgPutResponse);
                    } else if (object.getClass() == MsgExitRequest.class) {
                        System.out.println(name + " shutting down socket");
                    	break;
                    } else {
						System.out.println("invalid message on the socket");
						break;
					}
            	}
            	//close resources
                ois.close();
                oos.close();
            	socket.close();
        	} 
        	catch (IOException | ClassNotFoundException ex) {
        		System.out.println(ex.toString());
        	}
        }
    }

    //static ServerSocket variable
    public ServerSocket serverSocket;
    //socket server port on which it will listen
    private int port;
	private Hashtable<String, String> ht;
    public DHTServer() {
    	try {
    		port = 9876;
        	serverSocket = new ServerSocket(port);
			ht = new Hashtable<String, String>();
    	}
        catch (IOException ex){
        	System.out.println(ex.toString());
        }
    }
    
    public void createClientThread(Socket clientSocket, String clientName) {
    	ClientThread clientThread = new ClientThread(this, clientSocket, clientName);
    	clientThread.start();
    }

	public Hashtable<String, String> getHashtable() {
		return this.ht;
	}

    public static void main(String[] args) {
        try {
        	DHTServer dhtServer = new DHTServer();
        	//create the socket server object
        	String clientName = null;
        	int clientID = 0;
        	//keep listens indefinitely until receives 'exit' call or program terminates
        	while(true){
            	System.out.println("Waiting for the client request");
            	//creating socket and waiting for client connection
            	Socket socket = dhtServer.serverSocket.accept();
            	// new thread for a client
            	clientName = "CLIENT " + clientID;
            	clientID++;
            	dhtServer.createClientThread(socket, clientName);
            	if (clientID == 100) {
                	break;
            	}
        	}
        	System.out.println("Shutting down Socket server!!");
        	//close the ServerSocket object
        	dhtServer.serverSocket.close();
        }
        catch (IOException ex) {
        	System.out.println(ex.toString());
        }
    }
    
}