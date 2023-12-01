package client.interface_adapter.signup;

import client.interface_adapter.login.LoginState;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.send_message.SendMessageState;
import client.interface_adapter.ViewManagerModel;
import client.use_case.signup.SignupOutputBoundary;
import client.use_case.signup.SignupOutputData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
//    private final LoginViewModel loginViewModel;
    private final MainViewModel mainViewModel;
    private ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           MainViewModel mainViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
//        this.loginViewModel = loginViewModel;
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        LocalDateTime responseTime = LocalDateTime.parse(response.getCreationTime());
        response.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

//        LoginState loginState = loginViewModel.getState();
//        loginState.setUsername(response.getUsername());
//        this.loginViewModel.setState(loginState);
//        loginViewModel.firePropertyChanged();
//
//        viewManagerModel.setActiveView(loginViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
        // TODO: Check whether we should use other states.
        SendMessageState sendMessageState = mainViewModel.getSendMessageState();
        sendMessageState.setSender(response.getUsername());
        mainViewModel.setSendMessageState(sendMessageState);
        mainViewModel.fireSendMessagePropertyChanged();

        LoginState loginState = mainViewModel.getLoginState();
        loginState.setUsername(response.getUsername());
        mainViewModel.setLoginState(loginState);
        mainViewModel.fireLoginPropertyChanged();

        viewManagerModel.setActiveView(mainViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareLoginView() {
        viewManagerModel.setActiveView(mainViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SignupState signupState = signupViewModel.getState();
        signupState.setUsernameError(error);
        signupViewModel.firePropertyChanged();
    }
}
