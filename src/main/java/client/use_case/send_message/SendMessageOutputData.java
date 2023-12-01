package client.use_case.send_message;

public class SendMessageOutputData {
    private final boolean success;
    private final long timestamp;

    private final String message;

    /**
     * Constructs a SendMessageOutputData object with the provided success status and timestamp.
     *
     * @param success   The success status of the message send operation.
     * @param timestamp The timestamp of the message send operation.
     */
    public SendMessageOutputData(boolean success, long timestamp, String message) {
        this.success = success;
        this.timestamp = timestamp;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

}