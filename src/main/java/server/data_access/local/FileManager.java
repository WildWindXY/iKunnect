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

public class FileManager {
    private final DataAccess dataAccess;
    private final ServerUsers serverUsers;

    private static final ConcurrentLinkedQueue<IFile<?>> modifiedFiles = new ConcurrentLinkedQueue<>();
    private static final String TEST_PATH = "E:\\Desktop\\data\\";
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
                saveModifiedConfigs();
            }
        }, 1000, 1000);// execute every second
    }

    public ServerUser getUserById(int userId) {
        return serverUsers.getUser(userId);
    }

    public ServerUser getUserByUsername(int username) {
        return serverUsers.getUser(username);
    }

    public static <T extends IFile<T>> T registerModifiedFile(T file) {
        if (!modifiedFiles.contains(file)) {
            modifiedFiles.add(file);
        }
        return file;
    }

    private void saveModifiedConfigs() {
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

    public void shutdown() {
        timer.cancel();
    }

    public static void main(String[] args) throws InterruptedException { //TODO: For temp testing, delete later
        FileManager fileManager = new FileManager(null);
        Thread.sleep(5000);
        fileManager.shutdown();
    }
}
