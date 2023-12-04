package com.xiaoheizi.client.interface_adapter.login;

import com.xiaoheizi.client.use_case.login.LoginInputBoundary;
import com.xiaoheizi.client.use_case.login.LoginInputData;

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
