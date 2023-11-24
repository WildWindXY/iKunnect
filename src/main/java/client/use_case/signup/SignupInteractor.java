package client.use_case.signup;


import client.entity.UserFactory;
import common.packet.PacketServerSignupResponse;

import java.time.LocalDateTime;

public class SignupInteractor implements SignupInputBoundary {
    final SignupDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(SignupDataAccessInterface signupDataAccessInterface, SignupOutputBoundary signupOutputBoundary, UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData data) {
        if (!data.getPassword().equals(data.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else {
            PacketServerSignupResponse packet = userDataAccessObject.signup(data.getUsername(), data.getPassword());
            if (packet.getStatus() == PacketServerSignupResponse.Status.SUCCESS) {
                SignupOutputData signupOutputData = new SignupOutputData(data.getUsername(), LocalDateTime.now().toString(), false);
                userPresenter.prepareSuccessView(signupOutputData);
            } else {
                userPresenter.prepareFailView(packet.toString());
            }
        }
    }
}