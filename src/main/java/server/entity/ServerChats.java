package server.entity;


import com.google.gson.annotations.Expose;
import server.data_access.local.FileManager;

import java.util.ArrayList;
import java.util.List;

public class ServerChats implements IFile<ServerChats> {
    public static final String PATH = "chats.json";

    private static ServerChats instance;
    @Expose
    private List<Chat> chats = new ArrayList<>();

    public ServerChats() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException("Only one ServerChats instance should exists");
        }
    }

    public static ServerChats getDefault() {
        return new ServerChats();
    }

    public static void save() {
        FileManager.registerModifiedFile(instance);
    }

    public ServerChat create() {
        Chat chat = new Chat(chats.size());
        chats.add(chat);
        return chat;
    }

    public ServerChat getChat(int id) {
        return chats.get(id);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    private static class Chat implements ServerChat {
        @Expose
        private int chatId;
        @Expose
        private List<Integer> messages = new ArrayList<>();

        private Chat(int chatId) {
            this.chatId = chatId;
        }

        public int getChatId() {
            return chatId;
        }

        public void addMessage(int messageId) {
            messages.add(messageId);
        }

        public List<Integer> getMessages() {
            return new ArrayList<>(messages);
        }
    }
}