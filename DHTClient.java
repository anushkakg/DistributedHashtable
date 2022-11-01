import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class implements a socket client of a distributed hashtable 
 *
 */
public class DHTClient {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        // get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
    	// establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
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