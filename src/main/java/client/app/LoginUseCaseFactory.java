package client.app;

import client.entity.CommonUserFactory;
import client.entity.UserFactory;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.Logged_in.LoggedInViewModel;
import client.interface_adapter.Login.LoginController;
import client.interface_adapter.Login.LoginPresenter;
import client.interface_adapter.Login.LoginViewModel;
import client.use_case.Login.LoginInputBoundary;
import client.use_case.Login.LoginInteractor;
import client.use_case.Login.LoginOutputBoundary;
import client.use_case.Login.LoginDataAccessInterface;
import client.view.LoginView;

import javax.swing.*;
import java.io.IOException;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            MainViewModel mainViewModel,
            LoginDataAccessInterface userDataAccessObject) {

        try {
            LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, mainViewModel, userDataAccessObject);
            return new LoginView(loginController, loginViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            MainViewModel mainViewModel,
            LoginDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, mainViewModel, loginViewModel);

        UserFactory userFactory = new CommonUserFactory();

        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        return new LoginController(loginInteractor);
    }
}
