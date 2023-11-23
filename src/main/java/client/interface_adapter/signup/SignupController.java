package client.interface_adapter.signup;

import client.use_case.signup.SignupInputBoundary;
import client.use_case.signup.SignupInputData;

public class SignupController {
    final SignupInputBoundary userSignupUseCaseInteractor;
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    public void execute(String username, String password1, String password2) {
        SignupInputData signupInputData = new SignupInputData(username, password1, password2);
        userSignupUseCaseInteractor.execute(signupInputData);
        System.out.println("SignupController.execute()");
    }
}
