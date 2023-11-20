package server.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ServerMessages implements IFile<ServerMessages> {
    public static final String PATH = "messages.json";

    private static ServerMessages instance;
    @Expose
    private List<TextMessage> messages = new ArrayList<>();

    public ServerMessages() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException("Only one ServerMessages instance should exists");
        }
    }

    public static ServerMessages getDefault() {
        return new ServerMessages();
    }

    public TextMessage addMessage(int senderId, String text) {
        TextMessage textMessage = new TextMessage(messages.size(), senderId, text);
        messages.add(textMessage);
        return textMessage;
    }

    public TextMessage getMessage(int id) {
        return messages.get(id);
    }

    @Override
    public String getPath() {
        return PATH;
    }


    public static class TextMessage {

        @Expose
        private int messageId;
        @Expose
        private int senderId;
        @Expose
        private String text;

        private TextMessage(int messageId, int senderId, String text) {
            this.messageId = messageId;
            this.senderId = senderId;
            this.text = text;
        }

        public int getSenderId() {
            return senderId;
        }

        public String getText() {
            return text;
        }

        public int getMessageId() {
            return messageId;
        }
    }
}
