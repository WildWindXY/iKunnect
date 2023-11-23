package client.app;

import client.entity.CommonUserFactory;
import client.entity.UserFactory;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.login.LoginController;
import client.interface_adapter.login.LoginPresenter;
import client.interface_adapter.login.LoginViewModel;
import client.use_case.login.LoginInputBoundary;
import client.use_case.login.LoginInteractor;
import client.use_case.login.LoginOutputBoundary;
import client.use_case.login.LoginDataAccessInterface;
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
            return new LoginView(loginController, loginViewModel);
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
