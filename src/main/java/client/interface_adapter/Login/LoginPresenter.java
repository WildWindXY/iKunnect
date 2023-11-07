package client.interface_adapter.Login;

import client.use_case.Signup.SignupOutputBoundary;
import client.use_case.Signup.SignupOutputData;


public class LoginPresenter implements SignupOutputBoundary {

    @Override
    public void prepareFailView(String s) {
    }

    @Override
    public void prepareSuccessView(SignupOutputData signupOutputData) {
    }
}
