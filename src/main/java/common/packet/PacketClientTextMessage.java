package common.packet;

public class PacketClientTextMessage implements Packet {
    private final String message;
    private final int recipientID;
    private final int clientMessageId;

    public PacketClientTextMessage(int clientMessageId, int recipientID, String message) {// TODO: change recipientID to chatID
        this.clientMessageId = clientMessageId; // Allows client to check if message was sent, server would respond with the same id
        this.recipientID = recipientID;
        this.message = message;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public int getRecipient() {
        return this.recipientID;
    }

    public int getClientMessageId() {
        return this.clientMessageId;
    }

    @Override
    public String toString() {
        return "[PacketClientMessage] clientMessageId: " + clientMessageId + ", message: " + message + ", recipientID: " + recipientID;
    }
}