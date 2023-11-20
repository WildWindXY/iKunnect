package client.use_case.PasswordChecker;

public class PasswordCheckerInputData {
    private final String password;
    public PasswordCheckerInputData(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
