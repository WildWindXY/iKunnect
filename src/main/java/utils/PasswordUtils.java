package utils;

public class PasswordUtils {

    public static boolean isStrongPassword(String password) {

        String passwordRegex = "^[A-Za-z0-9!@#$%^&*()-_+=<>?/]{8,}$";

        return password.matches(passwordRegex);
        // Password meets the criteria (8 characters or more, including letters, digits, and specified symbols)
    }
}

