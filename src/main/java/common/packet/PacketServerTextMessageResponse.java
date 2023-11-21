package common.packet;

public class PacketServerTextMessageResponse implements Packet {

    private final int clientMessageId;
    private final long timestamp;

    private final Status status;

    public PacketServerTextMessageResponse(int clientMessageId, long timestamp, Status status) {
        this.clientMessageId = clientMessageId;
        this.timestamp = System.currentTimeMillis();
        this.status = status;
    }

    public int getClientMessageId() {
        return clientMessageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "[PacketServerTextMessageResponse] clientMessageId: " + clientMessageId + ", timestamp: " + timestamp + ", status: " + status;
    }

    public enum Status {
        RECEIVED, NOT_LOGGED_IN, EMPTY_MESSAGE, NOT_FRIEND, SERVER_ERROR
    }
}
