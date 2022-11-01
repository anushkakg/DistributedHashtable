import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DHTServer {
	private static DHTServerNode dhtServerNode;
	private static InetSocketAddress contactAddress;
	private static DHTUtil dhtUtil;

	public static void main (String[] args) {
		
		dhtUtil = new DHTUtil();
		
		// get local machine's ip 
		String local_ip = null;
		try {
			local_ip = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// create node
		dhtServerNode = new DHTServerNode(DHTUtil.createSocketAddress(local_ip+":"+args[0]));
		
		// determine if it's creating or joining a existing ring
		// create, contact is this node itself
		if (args.length == 1) {
			contactAddress = dhtServerNode.getAddress();
		}
		
		// join, contact is another node
		else if (args.length == 3) {
			contactAddress = DHTUtil.createSocketAddress(args[1]+":"+args[2]);
			if (contactAddress == null) {
				System.out.println("Cannot find address you are trying to contact. Now exit.");
				return;
			}	
		}
		
		else {
			System.out.println("Wrong input. Now exit.");
			System.exit(0);
		}
		
		/* try to join ring from contact node
		boolean successful_join = dhtServerNode.join(contactAddress);
		
		// fail to join contact node
		if (!successful_join) {
			System.out.println("Cannot connect with node you are trying to contact. Now exit.");
			System.exit(0);
		}
		
		// print join info
		System.out.println("Joining the Chord ring.");
		System.out.println("Local IP: "+local_ip);
		dhtServerNode.printNeighbors();
		*/
		
		// begin to take user input, "info" or "quit"
		Scanner userinput = new Scanner(System.in);
		while(true) {
			System.out.println("\nType \"info\" to check this node's data or \n type \"quit\"to leave ring: ");
			String command = null;
			command = userinput.next();
			if (command.startsWith("quit")) {
				dhtServerNode.stopAllThreads();
				System.out.println("Leaving the ring...");
				System.exit(0);
				
			}
			else if (command.startsWith("info")) {
				dhtServerNode.printDataStructure();
			}
		}
	}

	/*


    //static ServerSocket variable
    public ServerSocket serverSocket;
    //socket server port on which it will listen
    private int port;

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
    */
}