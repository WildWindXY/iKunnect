package server.data_access.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class localStorageTemplate {

    //    public static final String storagePath = getJarPath();
    private static final List<IFile> modifiedFiles = new ArrayList<>();

    public localStorageTemplate() {
//        new File(Utils.getJarPath() + "iKunnectServer").mkdir();
//        new File(Utils.getJarPath() + "iKunnectServer\\data").mkdir();
    }


    public static void registerModifiedConfig(IFile file) {
        if (!modifiedFiles.contains(file)) {
            modifiedFiles.add(file);
        }
    }

    public static void startAutoSave() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                saveModifiedConfigs();
            }
        }, 1000, 1000);
    }

    private static void saveModifiedConfigs() {
        for (IFile file : modifiedFiles) {
//            Log.logStringAndSendChat("Saving file file: " + file.getPath());
            try (FileWriter fileWriter = new FileWriter(file.getPath())) {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                gson.toJson(file, fileWriter);
                modifiedFiles.remove(file);
            } catch (IOException ignored) {
                //TODO: handle
            }
        }
    }
}
