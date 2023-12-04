package com.xiaoheizi.client.interface_adapter.signup;

import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.use_case.signup.SignupOutputBoundary;
import com.xiaoheizi.client.use_case.signup.SignupOutputData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    //    private final LoginViewModel loginViewModel;
    private final MainViewModel mainViewModel;
    private final ViewManagerModel viewManagerModel;

    private final LoginViewModel loginViewModel;

    public SignupPresenter(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        LocalDateTime responseTime = LocalDateTime.parse(response.getCreationTime());
        response.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        mainViewModel.getLoginState().setUsername(response.getUsername());
        this.mainViewModel.setMyUserId(response.getUserId());
        mainViewModel.fireLoginPropertyChanged();

        viewManagerModel.setActiveView(mainViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareLoginView() {
        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChanged();
    }
}
