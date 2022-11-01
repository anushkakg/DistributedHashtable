import java.io.*;

// This class represents a protocol message request to put a key,value in the hashtable
public class MsgPutRequest implements java.io.Serializable {
    // key to be inserted into the hastable
    public String key;
    // value to be inserted into the hastable
    public String value;
    // constructor
    public MsgPutRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }
}