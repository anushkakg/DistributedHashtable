import java.io.*;

// This class represents a protocol message response to the PutRequest
public class MsgPutResponse implements java.io.Serializable, Outcome {
    public boolean success;
    public String error;

    // default contructor
    public MsgPutResponse() {
        this.success = true;
        this.error = null;
    }
    
    // contructor to override success and error
    public MsgPutResponse(boolean success, String error) {
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