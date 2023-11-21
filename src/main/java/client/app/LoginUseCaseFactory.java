package client.app;

import client.data_access.password_checker.PasswordCheckerDataAccess;
import client.entity.CommonUserFactory;
import client.entity.UserFactory;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.SignupController;
import client.interface_adapter.Signup.SignupPresenter;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.Logged_in.LoggedInViewModel;
import client.interface_adapter.Login.LoginController;
import client.interface_adapter.Login.LoginPresenter;
import client.interface_adapter.Login.LoginViewModel;
import client.use_case.Login.LoginInputBoundary;
import client.use_case.Login.LoginInteractor;
import client.use_case.Login.LoginOutputBoundary;
import client.use_case.Login.LoginDataAccessInterface;
import client.use_case.PasswordChecker.PasswordCheckerInputBoundary;
import client.use_case.PasswordChecker.PasswordCheckerInteractor;
import client.use_case.Signup.SignupDataAccessInterface;
import client.use_case.Signup.SignupInputBoundary;
import client.use_case.Signup.SignupInteractor;
import client.use_case.Signup.SignupOutputBoundary;
import client.view.LoginView;

import javax.swing.*;
import java.io.IOException;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            MainViewModel mainViewModel,
            LoginDataAccessInterface userDataAccessObject) {

        try {
            LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, userDataAccessObject);
            SignupDataAccessInterface signupUserDataAccessObject = (SignupDataAccessInterface) userDataAccessObject; // TODO: fix this (shouldn't be casting
            SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
            UserFactory userFactory = new CommonUserFactory();
            SignupInputBoundary userSignupInteractor = new SignupInteractor(
                    signupUserDataAccessObject, signupOutputBoundary, userFactory);
            PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
            SignupController signupController = new SignupController(userSignupInteractor,passwordCheckerUseCaseInteractor);
            return new LoginView(loginController, signupController, loginViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            SignupViewModel signupViewModel,
            MainViewModel mainViewModel,
            LoginDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);

        UserFactory userFactory = new CommonUserFactory();

        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        return new LoginController(loginInteractor);
    }
}
