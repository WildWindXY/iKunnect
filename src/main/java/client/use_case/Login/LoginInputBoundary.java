package client.use_case.Login;

public interface LoginInputBoundary {
    public void execute(LoginInputData loginInputData);
    public void executeSignup();
}
