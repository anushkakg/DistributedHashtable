import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class implements a socket client of a distributed hashtable 
 *
 */
public class DHTClient {
    

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        /*
        InetSocketAddress localAddress = null;
        DHTUtil dhtUtil = new DHTUtil();

		// valid args
		if (args.length == 1) {
            String local_ip = InetAddress.getLocalHost().getHostAddress();
			// try to parse socket address from args, if fail, exit
			localAddress = DHTUtil.createSocketAddress(local_ip+":"+args[0]);
			if (localAddress == null) {
				System.out.println("Cannot find address you are trying to contact. Now exit.");
				System.exit(0);;	
			}
        }
            /*
			// successfully constructed socket address of the node we are 
			// trying to contact, check if it's alive
			String response = DHTUtil.sendRequest(localAddress, "KEEP");

			// if it's dead, exit
			if (response == null || !response.equals("ALIVE"))  {
				System.out.println("\nCannot find node you are trying to contact. Now exit.\n");
				System.exit(0);
			}

			// it's alive, print connection info
			System.out.println("Connection to node " + localAddress.getAddress().toString() + ", port " + localAddress.getPort() + ", position " + DHTUtil.hexIdAndPosition(localAddress) + ".");
            
            
			// check if system is stable
			boolean pred = false;
			boolean succ = false;
			InetSocketAddress pred_addr = DHTUtil.requestAddress(localAddress, "YOURPRE");			
			InetSocketAddress succ_addr = DHTUtil.requestAddress(localAddress, "YOURSUCC");
			if (pred_addr == null || succ_addr == null) {
				System.out.println("The node your are contacting is disconnected. Now exit.");
				System.exit(0);	
			}
			if (pred_addr.equals(localAddress))
				pred = true;
			if (succ_addr.equals(localAddress))
				succ = true;

			// we suppose the system is stable if (1) this node has both valid 
			// predecessor and successor or (2) none of them
			while (pred^succ) {
				System.out.println("Waiting for the system to be stable...");
				pred_addr = DHTUtil.requestAddress(localAddress, "YOURPRE");			
				succ_addr = DHTUtil.requestAddress(localAddress, "YOURSUCC");
				if (pred_addr == null || succ_addr == null) {
					System.out.println("The node your are contacting is disconnected. Now exit.");
					System.exit(0);	
				}
				if (pred_addr.equals(localAddress))
					pred = true;
				else 
					pred = false;
				if (succ_addr.equals(localAddress))
					succ = true;
				else 
					succ = false;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}

			}
        
        */
        
        // get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
    	// establish socket connection to server
        socket = new Socket(host.getHostName(), Integer.parseInt(args[0]));
        // create an ObjectOutputStream for the socket
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        // create an ObjectInputStream for the socket
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        String request = null, key = null, value = null;
        // take user input, convert it into approporiate class
        while (true) {
            Scanner userinput = new Scanner(System.in);
            System.out.println("enter type of request");
            request = userinput.nextLine();
            
            // put
            if (request.equals("put")) {
                System.out.println("enter key");
                key = userinput.nextLine();
                System.out.println("enter value");
                value = userinput.nextLine();
                MsgPutRequest msgPutRequest = new MsgPutRequest(key, value);
                outputStream.writeObject(msgPutRequest);
                MsgPutResponse msgPutResponse = (MsgPutResponse) inputStream.readObject();
                if (msgPutResponse.success) {
                    System.out.println("put successful");
                } else {
                    System.out.println(msgPutResponse.error);
                }
            } 
            // get
            else if (request.equals("get")) {
                System.out.println("enter key");
                key = userinput.nextLine();
                MsgGetRequest msgGetRequest = new MsgGetRequest(key);
                outputStream.writeObject(msgGetRequest);
                MsgGetResponse msgGetResponse = (MsgGetResponse) inputStream.readObject();
                if (msgGetResponse.success) {
                    System.out.println(msgGetResponse.value);
                } else {
                    System.out.println(msgGetResponse.error);
                }
            } 
            // exit
            else if (request.equals("exit")) {
                MsgExitRequest msgExitRequest = new MsgExitRequest();
                outputStream.writeObject(msgExitRequest);
                break;
            } else {
                System.out.println("invalid command");
                break;
            }
        }
        //close resources
        inputStream.close();
        outputStream.close();
    }
}