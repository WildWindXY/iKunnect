package client.interface_adapter.signup;

<<<<<<< HEAD:src/main/java/client/interface_adapter/Signup/SignupController.java
import client.use_case.PasswordChecker.PasswordCheckerInputBoundary;
import client.use_case.PasswordChecker.PasswordCheckerInputData;
import client.use_case.Signup.SignupInputBoundary;
import client.use_case.Signup.SignupInputData;
=======
import client.use_case.signup.SignupInputBoundary;
import client.use_case.signup.SignupInputData;
>>>>>>> ui_development_jiayou_liu:src/main/java/client/interface_adapter/signup/SignupController.java

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

    public boolean checkPassword(String password) {
        return passwordCheckerUseCaseInteractor.execute(new PasswordCheckerInputData(password));
    }
}
