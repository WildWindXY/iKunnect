package com.xiaoheizi.server.data_access.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.xiaoheizi.server.data_access.DataAccess;
import com.xiaoheizi.server.entity.*;
import com.xiaoheizi.utils.FileUtils;
import com.xiaoheizi.utils.TextUtils;
import com.xiaoheizi.utils.Triple;
import com.xiaoheizi.utils.Tuple;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The FileManager class handles file-related operations and manages user data on the com.ikun.server.
 */
public class FileManager {
    private static final ConcurrentLinkedQueue<IFile<?>> modifiedFiles = new ConcurrentLinkedQueue<>();
    private static final String TEST_PATH = '/' + FileUtils.getJarPath();
    private final DataAccess dataAccess;
    private final ServerUsers serverUsers;

    private final ServerChats serverChats;
    private final ServerMessages serverMessages;
    private final Timer timer;

    private final Thread shutdownHook = new Thread(() -> {
        System.err.println("Emergency Saving...");
        saveModifiedFiles();
        System.err.println("Savings Done.");
    });

    public FileManager(DataAccess dataAccess) {
        File dir = new File(TEST_PATH);
        System.out.println(TEST_PATH);
        if (!dir.exists() && !dir.mkdir()) {
            dataAccess.addTerminalMessage(TextUtils.error("Warning: Unable to create dir at: " + TEST_PATH + ", system closing..."));
            System.exit(0x2304);
        }
        this.dataAccess = dataAccess;
        serverUsers = loadServerUsers();
        serverChats = loadServerChats();
        serverMessages = loadServerMessages();
        timer = new Timer("Timer save modified files");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveModifiedFiles();
            }
        }, 1000, 1000);// execute every second
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * Registers a modified file to the queue.
     *
     * @param file The file to be registered.
     * @return The registered file.
     */
    public static <T extends IFile<T>> T registerModifiedFile(T file) {
        if (!modifiedFiles.contains(file)) {
            modifiedFiles.add(file);
        }
        return file;
    }

    /**
     * Gets a com.ikun.server user by user ID.
     *
     * @param userId The user ID.
     * @return The ServerUser with the specified user ID.
     */
    public ServerUser getUserById(int userId) {
        return serverUsers.getUser(userId);
    }

    /**
     * Gets a com.ikun.server user by username.
     *
     * @param username The username.
     * @return The ServerUser with the specified username.
     */
    public ServerUser getUserByUsername(String username) {
        return serverUsers.getUser(username);
    }

    /**
     * Adds a new user with the specified username and password to the com.ikun.server user database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The ServerUser representing the newly added user.
     */
    public ServerUser addUser(String username, String password) {
        return serverUsers.addUser(username, password);
    }

    public ServerChat createChat() {
        return serverChats.create();
    }

    public ServerChat getChat(int id) {
        return serverChats.getChat(id);
    }

    public HashMap<Integer, List<Triple<Long, Integer, String>>> getChats(Collection<Integer> chatIds) {
        HashMap<Integer, List<Integer>> map = serverChats.getChatMessageIds(chatIds);
        HashMap<Integer, List<Triple<Long, Integer, String>>> chats = new HashMap<>();
        for (int chatId : map.keySet()) {
            List<Triple<Long, Integer, String>> messages = new ArrayList<>();
            for (int messageId : map.get(chatId)) {
                ServerMessages.TextMessage textMessage = serverMessages.getMessage(messageId);
                messages.add(new Triple<>(textMessage.getTimestamp(), textMessage.getSenderId(), textMessage.getText()));
            }
            chats.put(chatId, messages);
        }
        return chats;
    }

    public int addMessage(int senderId, String text) {
        return serverMessages.addMessage(senderId, text);
    }

    /**
     * Checks the password for a given username in the com.ikun.server user database.
     *
     * @param username The username to check for.
     * @param password The password to validate against the stored password.
     * @return A Tuple representing the result of the password check.
     * - If the username and password match, returns Tuple with integer 0 and the corresponding ServerUser.
     * - If the username exists but the password does not match, returns Tuple with integer -1 and null.
     * - If the username does not exist, returns Tuple with integer -2 and null.
     */
    public Tuple<Integer, ServerUser> checkPassword(String username, String password) {
        return serverUsers.checkPassword(username, password);
    }

    /**
     * Initiates a graceful shutdown of the FileManager.
     */
    public void shutdown() {
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
        timer.cancel();
        saveModifiedFiles();
    }

    private void saveModifiedFiles() {
        for (IFile<?> file : modifiedFiles) {
            try (FileWriter fileWriter = new FileWriter(TEST_PATH + file.getPath())) {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                gson.toJson(file, fileWriter);
            } catch (IOException e) {
                dataAccess.addTerminalMessage(TextUtils.error("Error saving config file: " + TEST_PATH + file.getPath()));
            }
        }
        modifiedFiles.clear();
    }

    private ServerUsers loadServerUsers() {
        File file = new File(TEST_PATH + ServerUsers.PATH);
        if (!file.exists()) {
            return registerModifiedFile(ServerUsers.getDefault());
        }
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            ServerUsers fileIn = gson.fromJson(fileReader, ServerUsers.class);
            if (fileIn == null) {
                dataAccess.addTerminalMessage(TextUtils.error("Warning: Invalid or corrupt users file at: " + TEST_PATH + file.getPath()));
                return registerModifiedFile(ServerUsers.getDefault());
            }
            return fileIn;
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            dataAccess.addTerminalMessage(TextUtils.error("Warning: Error reading users file at: " + TEST_PATH + file.getPath()) + "with exception " + e.getMessage());
            return registerModifiedFile(ServerUsers.getDefault());
        }
    }

    private ServerChats loadServerChats() {
        File file = new File(TEST_PATH + ServerChats.PATH);
        if (!file.exists()) {
            return registerModifiedFile(ServerChats.getDefault());
        }
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            ServerChats fileIn = gson.fromJson(fileReader, ServerChats.class);
            if (fileIn == null) {
                dataAccess.addTerminalMessage(TextUtils.error("Warning: Invalid or corrupt users file at: " + TEST_PATH + file.getPath()));
                return registerModifiedFile(ServerChats.getDefault());
            }
            return fileIn;
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            dataAccess.addTerminalMessage(TextUtils.error("Warning: Error reading chats file at: " + TEST_PATH + file.getPath()) + "with exception " + e.getMessage());
            return registerModifiedFile(ServerChats.getDefault());
        }
    }

    private ServerMessages loadServerMessages() {
        File file = new File(TEST_PATH + ServerMessages.PATH);
        if (!file.exists()) {
            return registerModifiedFile(ServerMessages.getDefault());
        }
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            ServerMessages fileIn = gson.fromJson(fileReader, ServerMessages.class);
            if (fileIn == null) {
                dataAccess.addTerminalMessage(TextUtils.error("Warning: Invalid or corrupt users file at: " + TEST_PATH + file.getPath()));
                return registerModifiedFile(ServerMessages.getDefault());
            }
            return fileIn;
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            dataAccess.addTerminalMessage(TextUtils.error("Warning: Error reading chats file at: " + TEST_PATH + file.getPath()) + "with exception " + e.getMessage());
            return registerModifiedFile(ServerMessages.getDefault());
        }
    }

}
