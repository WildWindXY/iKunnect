package client.use_case.receive_message;

@SuppressWarnings("record")
public class ReceiveMessageOutputData {
    private final String message;

    private final int senderID;
    private final long timestamp;

    public ReceiveMessageOutputData(int senderID, String message, long timestamp) {
        this.senderID = senderID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getSenderID() {
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