package client.use_case.Login;

import common.packet.PacketServerLoginResponse;

public class LoginInteractor implements LoginInputBoundary {
    final LoginDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginDataAccessInterface userDataAccessInterface, LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        PacketServerLoginResponse packet = userDataAccessObject.login(loginInputData.getUsername(), loginInputData.getPassword());
        if (packet.getStatus() == PacketServerLoginResponse.Status.SUCCESS) {
            LoginOutputData loginOutputData = new LoginOutputData(loginInputData.getUsername(), false);
            loginPresenter.prepareSuccessView(loginOutputData);
        } else {
            loginPresenter.prepareFailView(packet.toString());
        }
    }

    @Override
    public void executeSignup() {
        loginPresenter.prepareSignupView();
    }
}
