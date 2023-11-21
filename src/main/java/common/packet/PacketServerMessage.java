package common.packet;

public class PacketServerMessage implements Packet {
    private final String message;
    private final int senderID;

    private final long timestamp;

    public PacketServerMessage(int senderID, String message, long timestamp) {
        this.senderID = senderID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public int getSender() {
        return this.senderID;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "[PacketServerMessage] senderID: " + senderID + ", timestamp: " + timestamp + ", message: " + message;
    }
}
