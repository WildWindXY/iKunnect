package client.use_case.sendMessage;

public class SendMessageOutputData {
    private final boolean success;
    private final long timestamp;

    /**
     * Constructs a SendMessageOutputData object with the provided success status and timestamp.
     *
     * @param success   The success status of the message send operation.
     * @param timestamp The timestamp of the message send operation.
     */
    public SendMessageOutputData(boolean success, long timestamp) {
        this.success = success;
        this.timestamp = timestamp;
    }

    /**
     * Get the success status of the message send operation.
     *
     * @return The success status of the message send operation.
     */
    public boolean getSuccess() {
        return success;
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