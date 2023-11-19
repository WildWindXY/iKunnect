package server.data_access.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import server.data_access.DataAccess;
import server.entity.IFile;
import server.entity.ServerUser;
import server.entity.ServerUsers;
import utils.TextUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The FileManager class handles file-related operations and manages user data on the server.
 */
public class FileManager {
    private static final ConcurrentLinkedQueue<IFile<?>> modifiedFiles = new ConcurrentLinkedQueue<>();
    private static final String TEST_PATH = "E:\\Desktop\\data\\";
    private final DataAccess dataAccess;
    private final ServerUsers serverUsers;
    private final Timer timer;

    public FileManager(DataAccess dataAccess) {
        File dir = new File("E:\\Desktop\\data");
        if (!dir.exists() && !dir.mkdir()) {
            dataAccess.addTerminalMessage(TextUtils.error("Warning: Unable to create dir at: E:\\Desktop\\data, system closing..."));
            System.exit(0x2304);
        }
        this.dataAccess = dataAccess;
        serverUsers = loadServerUsers();
        timer = new Timer("Timer save modified files");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveModifiedFiles();
            }
        }, 1000, 1000);// execute every second
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
     * Gets a server user by user ID.
     *
     * @param userId The user ID.
     * @return The ServerUser with the specified user ID.
     */
    public ServerUser getUserById(int userId) {
        return serverUsers.getUser(userId);
    }

    /**
     * Gets a server user by username.
     *
     * @param username The username.
     * @return The ServerUser with the specified username.
     */
    public ServerUser getUserByUsername(String username) {
        return serverUsers.getUser(username);
    }

    /**
     * Adds a new user with the specified username and password to the server user database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The ServerUser representing the newly added user.
     */
    public ServerUser addUser(String username, String password) {
        return serverUsers.addUser(username, password);
    }

    /**
     * Initiates a graceful shutdown of the FileManager.
     */
    public void shutdown() {
        timer.cancel();
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

}
