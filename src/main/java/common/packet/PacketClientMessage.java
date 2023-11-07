package common.packet;

public class PacketClientMessage implements Packet {
    private final String message;
    private final long recipientID;


    public PacketClientMessage(long recipientID, String message) {
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
