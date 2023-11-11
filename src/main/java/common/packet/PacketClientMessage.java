package common.packet;

public class PacketClientMessage implements Packet {
    private final String message;
    private final String recipientID;

    public PacketClientMessage(String recipientID, String message) {

        this.recipientID = recipientID;
        this.message = message;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public String getRecipient() {
        return this.recipientID;
    }

    @Override
    public String toString() {
        return String.valueOf(recipientID) + " " + message;
    }
}
