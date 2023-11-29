package client.use_case.login;

public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData);
    void executeSignup();
}
