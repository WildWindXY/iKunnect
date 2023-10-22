package client.use_case.Signup;

import client.view.SignupView;

import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.Signup.*;


import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {

    /** Prevent instantiation. */
    private SignupUseCaseFactory() {}

    //create SignupView
    public static SignupView create(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel) {
        return null;
    }

    //create SignupController
    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel){
        return null;
    }
}
