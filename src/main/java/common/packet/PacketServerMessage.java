package common.packet;

public class PacketServerMessage implements Packet{
    private final String message;
    private final String senderID;

    private final long timestamp;

    public PacketServerMessage(String senderID, String message, long timestamp) {
        this.senderID = senderID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public String getSender() {
        return this.senderID;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
