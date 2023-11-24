package client.use_case.login;

public interface LoginInputBoundary {
    public void execute(LoginInputData loginInputData);
    public void executeSignup();
}
