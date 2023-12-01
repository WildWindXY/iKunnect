package client.use_case.login;

import common.packet.PacketServerGetFriendListResponse;
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
            PacketServerGetFriendListResponse packet0 = userDataAccessObject.getFriendList();
            System.out.println(packet0);
            if (packet0.getStatus() == PacketServerGetFriendListResponse.Status.SUCCESS) {
                LoginOutputData loginOutputData = new LoginOutputData(loginInputData.getUsername(), packet0.getFriends(), packet0.getChats(), packet.getUserID());
                loginPresenter.prepareSuccessView(loginOutputData);
            } else {
                loginPresenter.prepareFailView("Your login is successful, but:\n " + packet);
            }
        } else {
            loginPresenter.prepareFailView(packet.toString());
        }
    }

    @Override
    public void executeSignup() {
        loginPresenter.prepareSignupView();
    }
}
