package client.use_case.send_message;

public class SendMessageInputData {
    private final String message;
    private final String senderID;
    private final int recipientID;

    /**
     * Constructs a SendMessageInputData object with the provided message content, sender, and receiver.
     *
     * @param message  The content of the message.
     * @param sender   The ID of the message sender.
     * @param receiver The ID of the message receiver.
     */
    public SendMessageInputData(String message, String sender, int recipientID) {
        this.message = message;
        this.senderID = sender;
        this.recipientID = recipientID;
    }

    /**
     * Get the message content.
     *
     * @return The content of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the ID of the message sender.
     *
     * @return The ID of the message sender.
     */
    public String getSender() {
        return senderID;
    }

    /**
     * Get the ID of the message receiver.
     *
     * @return The ID of the message receiver.
     */
    public int getRecipientID() {
        return recipientID;
    }
}