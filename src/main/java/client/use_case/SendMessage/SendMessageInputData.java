package client.use_case.SendMessage;

public class SendMessageInputData {
    private final String message;
    private final String senderID;
    private final String recipientID;

    public SendMessageInputData(String message, String sender, String receiver) {
        this.message = message;
        this.senderID = sender;
        this.recipientID = receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return senderID;
    }

    public String getReceiver() {
        return recipientID;
    }
}