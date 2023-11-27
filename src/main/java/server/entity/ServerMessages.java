package server.entity;

import com.google.gson.annotations.Expose;
import server.data_access.local.FileManager;

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

    public static void save() {
        FileManager.registerModifiedFile(instance);
    }

    public static ServerMessages getDefault() {
        return new ServerMessages();
    }

    public int addMessage(int senderId, String text) {
        int messageId = messages.size();
        TextMessage textMessage = new TextMessage(messageId, senderId, text);
        messages.add(textMessage);
        return messageId;
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
        private long timestamp;
        @Expose
        private String text;

        private TextMessage(int messageId, int senderId, String text) {
            this.messageId = messageId;
            this.senderId = senderId;
            this.text = text;
            this.timestamp = System.currentTimeMillis();
        }

        public int getSenderId() {
            return senderId;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getText() {
            return text;
        }

        public int getMessageId() {
            return messageId;
        }
    }
}
