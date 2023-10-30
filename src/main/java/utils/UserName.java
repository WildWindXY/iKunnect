package utils;

public class UserName {
    public static boolean isValidName(String username) {

        String legalName = "^[A-Za-z0-9_.-]{2,}$";

        return username.matches(legalName);
        // Password meets the criteria (8 characters or more, including letters, digits, and specified symbols)
    }
}
