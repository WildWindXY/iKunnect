package client.use_case.SendMessage;

public class SendMessageOutputData{
    private final boolean success;
    private final long timestamp;

    public SendMessageOutputData(boolean success, long timestamp) {
        this.success = success;
        this.timestamp = timestamp;
    }

    public boolean getSuccess() {
        return success;
    }

    public long getTimestamp() {
        return timestamp;
    }

}