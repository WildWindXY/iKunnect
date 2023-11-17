package client.use_case.PasswordChecker;

public class PasswordCheckerOutputData {
    private final boolean isValid;
    public PasswordCheckerOutputData(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }
}
