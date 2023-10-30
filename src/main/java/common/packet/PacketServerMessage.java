package common.packet;

public class PacketServerMessage implements Packet{
    private final String message;
    private final long recipientID;

    public PacketServerMessage(long recipientID, String message) {
        this.recipientID = recipientID;
        this.message = message;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public long getRecipient() {
        return this.recipientID;
    }
}
