package client.app;

import client.interface_adapter.Login.LoginViewModel;
import client.use_case.Signup.SignupDataAccessInterface;
import client.view.SignupView;

import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.Signup.*;

public class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {}

    //create SignupView
    public static SignupView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, SignupViewModel signupViewModel, SignupDataAccessInterface userDataAccessObject) {
        return null;
    }

    //create SignupController
    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel){
        return null;
    }
}
