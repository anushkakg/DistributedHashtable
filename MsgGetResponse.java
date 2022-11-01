import java.io.*;

// This class represents a protocol message response to the GetRequest
public class MsgGetResponse implements java.io.Serializable, Outcome {
    // value for the key 
    public String value = null;
    public boolean success = false;
    public String error = null;

    // default contructor
    public MsgGetResponse(String value) {
        this.value = value;
        this.success = true;
        this.error = null;
    }
    
    // contructor to override value, success, and error
    public MsgGetResponse(String value, boolean success, String error) {
        this.value = value;
        this.success = success;
        this.error = error;
    }
    
    // returns whether outcome is successful
    public boolean success() {
        return this.success;
    }

    // returns error code if outcome is unsuccessful
    public String error() {
        return this.error;
    }
}