// this interface represents the success or failure reponse from the server
public interface Outcome {
    // returns whether outcome is successful
    boolean success();
    // returns error code if outcome is unsuccessful
    String error();
}