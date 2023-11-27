package client.app;

import client.data_access.ServerDataAccessObject;
import client.data_access.password_checker.PasswordCheckerDataAccess;
import client.entity.CommonUserFactory;
import client.entity.UserFactory;
import client.interface_adapter.Login.LoginController;
import client.interface_adapter.Login.LoginPresenter;
import client.interface_adapter.Login.LoginViewModel;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.SignupController;
import client.interface_adapter.Signup.SignupPresenter;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.use_case.Login.LoginInteractor;
import client.use_case.Login.LoginOutputBoundary;
import client.use_case.PasswordChecker.PasswordCheckerInputBoundary;
import client.use_case.PasswordChecker.PasswordCheckerInteractor;
import client.use_case.Signup.SignupInputBoundary;
import client.use_case.Signup.SignupInteractor;
import client.use_case.Signup.SignupOutputBoundary;
import client.view.LoginView;

public class LoginUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private LoginUseCaseFactory() {
    }

    public static LoginView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, ServerDataAccessObject serverDataAccessObject) {
        LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, mainViewModel);
        UserFactory userFactory = new CommonUserFactory();
        SignupInputBoundary userSignupInteractor = new SignupInteractor(serverDataAccessObject, signupOutputBoundary, userFactory);
        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
        SignupController signupController = new SignupController(userSignupInteractor, passwordCheckerUseCaseInteractor);
        return new LoginView(loginController, signupController, loginViewModel);
    }

    public static LoginController createLoginUseCase(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, ServerDataAccessObject serverDataAccessObject) {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
        return new LoginController(new LoginInteractor(serverDataAccessObject, loginOutputBoundary));
    }
}
