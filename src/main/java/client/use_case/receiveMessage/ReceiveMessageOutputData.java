package client.use_case.receiveMessage;

public class ReceiveMessageOutputData {
    private final String message;

    private final String senderID;
    private final long timestamp;

    public ReceiveMessageOutputData(int senderID, String message, long timestamp) {
        this.senderID = String.valueOf(senderID);
        this.message = message;
        this.timestamp = timestamp;
    }
    
    public String getSenderID() {
        return senderID;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Get the timestamp of the message send operation.
     *
     * @return The timestamp of the message send operation.
     */
    public long getTimestamp() {
        return timestamp;
    }

}