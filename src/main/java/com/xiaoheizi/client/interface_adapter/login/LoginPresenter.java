package com.xiaoheizi.client.interface_adapter.login;

import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.use_case.login.LoginOutputBoundary;
import com.xiaoheizi.client.use_case.login.LoginOutputData;


//public class LoginPresenter implements LoginOutputBoundary {
//
//    @Override
//    public void prepareSuccessView(LoginOutputData user) {
//        System.out.println("Login success");
//    }
//
//    @Override
//    public void prepareFailView(String s) {
//        System.out.println("Login fail");
//    }
//
//}

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SignupViewModel signupViewModel;
    private final MainViewModel mainViewModel;
    private ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.mainViewModel = mainViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the main view

        //LoginState loginState = mainViewModel.getLoginState();
        LoginState loginState = loginViewModel.getState();
        //sendMessageState.setSender(response.getUsername());

        this.mainViewModel.initMessages(response.getFriends(), response.getChats());
        this.mainViewModel.setMyUserId(response.getUserId());
        this.mainViewModel.setLoginState(loginState);
        this.mainViewModel.fireLoginPropertyChanged();

        this.viewManagerModel.setActiveView(mainViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        if (error.contains("password")) {
            loginState.setPasswordError(error);
        } else {
            loginState.setUsernameError(error);
        }
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSignupView() {
        this.viewManagerModel.setActiveView(signupViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
