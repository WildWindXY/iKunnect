package client.interface_adapter.signup;

import client.use_case.password_checker.PasswordCheckerInputBoundary;
import client.use_case.password_checker.PasswordCheckerInputData;
import client.use_case.signup.SignupInputBoundary;
import client.use_case.signup.SignupInputData;

public class SignupController {
    final SignupInputBoundary userSignupUseCaseInteractor;

    final PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor;
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor, PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
        this.passwordCheckerUseCaseInteractor = passwordCheckerUseCaseInteractor;
    }

    public void execute(String username, String password1, String password2) {
        SignupInputData signupInputData = new SignupInputData(username, password1, password2);
        userSignupUseCaseInteractor.execute(signupInputData);
        System.out.println("SignupController.execute()");
    }

    public void executeLogin(){
        userSignupUseCaseInteractor.executeLogin();
    }

    public boolean checkPassword(String password) {
        return passwordCheckerUseCaseInteractor.execute(new PasswordCheckerInputData(password));
    }
}
