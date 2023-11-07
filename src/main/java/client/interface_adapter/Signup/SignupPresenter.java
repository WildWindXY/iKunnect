package client.interface_adapter.Signup;

import client.use_case.Signup.SignupOutputBoundary;
import client.use_case.Signup.SignupOutputData;


public class SignupPresenter implements SignupOutputBoundary {

    @Override
    public void prepareFailView(String s) {
    }

    @Override
    public void prepareSuccessView(SignupOutputData signupOutputData) {
    }
}
