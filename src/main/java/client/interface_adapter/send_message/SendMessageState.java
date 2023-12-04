package client.interface_adapter.send_message;

public class SendMessageState {
    private String message = "";
    private long timestamp = 0;
    private int senderId = -1;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(long timestamp, int senderId, String message) {
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
