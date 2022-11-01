import java.io.*;

// This class represents a protocol message request to get the hashtable key
public class MsgGetRequest implements java.io.Serializable {
    // key for the hashtable
    public String key;
    public MsgGetRequest(String key) {
        this.key = key;
    }
}