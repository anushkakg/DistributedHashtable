import java.net.InetSocketAddress;
import java.util.*;
import java.io.*;

public class DHTServerNode {
    private DHTServerListener dhtServerListener;
	private Hashtable<String, String> ht;
	private InetSocketAddress localAddress;

    public DHTServerNode(InetSocketAddress address) {
		localAddress = address;
		ht = new Hashtable<String, String>();
		dhtServerListener = new DHTServerListener(this);
		dhtServerListener.start();
    }

	public InetSocketAddress getAddress() {
		return localAddress;
	}

	public String getLocal(String key) {
		return ht.get(key);
	}

	public void putLocal(String key, String value) {
		ht.put(key, value);
	}

	public void printDataStructure () {
		/*
		System.out.println("\n==============================================================");
		System.out.println("\nLOCAL:\t\t\t\t"+localAddress.toString()+"\t"+Helper.hexIdAndPosition(localAddress));
		if (predecessor != null)
			System.out.println("\nPREDECESSOR:\t\t\t"+predecessor.toString()+"\t"+Helper.hexIdAndPosition(predecessor));
		else 
			System.out.println("\nPREDECESSOR:\t\t\tNULL");
		System.out.println("\nFINGER TABLE:\n");
		for (int i = 1; i <= 32; i++) {
			long ithstart = Helper.ithStart(Helper.hashSocketAddress(localAddress),i);
			InetSocketAddress f = finger.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(i+"\t"+ Helper.longTo8DigitHex(ithstart)+"\t\t");
			if (f!= null)
				sb.append(f.toString()+"\t"+Helper.hexIdAndPosition(f));

			else 
				sb.append("NULL");
			System.out.println(sb.toString());
		}
		System.out.println("\n==============================================================\n");
		*/
	}

	/**
	 * Stop this node's all threads.
	 */
	public void stopAllThreads() {
		if (dhtServerListener != null)
			dhtServerListener.toDie();
		/*
		if (fix_fingers != null)
			fix_fingers.toDie();
		if (stabilize != null)
			stabilize.toDie();
		if (ask_predecessor != null)
			ask_predecessor.toDie();
		*/
	}

}