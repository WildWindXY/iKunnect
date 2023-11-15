package client.use_case.ReceiveMessage;

public class ReceiveMessageOutputData {
    private final String message;

    private final String senderID;
    private final long timestamp;

    /**
     * Constructs a SendMessageOutputData object with the provided success status and timestamp.
     *
     * @param success   The success status of the message send operation.
     * @param timestamp The timestamp of the message send operation.
     */
    public ReceiveMessageOutputData(String senderID, String message,  long timestamp) {
        this.senderID = senderID;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Get the success status of the message send operation.
     *
     * @return The success status of the message send operation.
     */
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