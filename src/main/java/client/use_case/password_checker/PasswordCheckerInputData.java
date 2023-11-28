package client.use_case.password_checker;

public class PasswordCheckerInputData {
    private final String password;
    public PasswordCheckerInputData(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
