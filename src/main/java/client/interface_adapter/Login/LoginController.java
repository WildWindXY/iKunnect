package client.interface_adapter.Login;

import client.use_case.Login.LoginInputBoundary;
import client.use_case.Login.LoginInputData;
import client.use_case.Signup.SignupInputBoundary;
import client.use_case.Signup.SignupInputData;

public class LoginController {
    final LoginInputBoundary loginUseCaseInteractor;
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    public void execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(username, password);
        loginUseCaseInteractor.execute(loginInputData);
    }

    public void executeSignup() {
        loginUseCaseInteractor.executeSignup();
    }
}
