package com.xiaoheizi.utils;

public class FileUtils {
    private static String jarPath;

    public static String getJarPath() {
        if (jarPath == null) {
            String className = new Exception().getStackTrace()[0].getClassName();
            try {
                String filepath = Class.forName(className).getProtectionDomain().getCodeSource().getLocation().toString();
                filepath = filepath.split("!")[0];
                filepath = filepath.substring(filepath.indexOf("/") + 1, filepath.lastIndexOf("/") + 1);
                jarPath = filepath;
                return filepath;
            } catch (ClassNotFoundException ignored) {
                return null;
            }
        } else {
            return jarPath;
        }
    }
}
