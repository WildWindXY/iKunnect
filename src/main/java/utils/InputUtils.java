package utils;

import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;

public class InputUtils {

    public static boolean isValidPasswordChar(char ch) {

        // Check if the character is a letter, digit, symbol, or backspace
        return Character.isLetterOrDigit(ch) || isSymbol(ch);
    }

    public static boolean isValidUsernameChar(char ch) {

        // Check if the character is a letter, digit, hyphen or underscore
        return Character.isLetterOrDigit(ch) || isCJKCharacter(ch) || ch == '-' || ch == '_';
    }

    public static boolean isBackspace(char ch) {
        return ch == '\b';
    }

    public static boolean isDelete(char ch) {
        return ch == '\u007f';
    }

    private static boolean isSymbol(char ch) {
        // Define your set of valid symbols here
        // For example: !@#$%^&*()-_+=
        String symbols = "!@#$%^&*()-_+=[](){};:'\"";
        return symbols.contains(String.valueOf(ch));
    }

    public static boolean isCJKCharacter(char c) {

        return Character.UnicodeBlock.of(c) == CJK_UNIFIED_IDEOGRAPHS;
    }

    public static String cleanInput(String s) {
        //A util class for cleaning the text for a message
        //TODO implement this

        return s.trim().isEmpty() ? "" : s.trim();
    }
}
