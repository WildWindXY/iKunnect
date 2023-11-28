package client.app;

import client.data_access.ServerDataAccessObject;
import client.data_access.password_checker.PasswordCheckerDataAccess;
import client.entity.CommonUserFactory;
import client.entity.UserFactory;
import client.interface_adapter.login.LoginController;
import client.interface_adapter.login.LoginPresenter;
import client.interface_adapter.login.LoginViewModel;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.signup.SignupController;
import client.interface_adapter.signup.SignupPresenter;
import client.interface_adapter.signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.use_case.login.LoginInteractor;
import client.use_case.login.LoginOutputBoundary;
import client.use_case.password_checker.PasswordCheckerInputBoundary;
import client.use_case.password_checker.PasswordCheckerInteractor;
import client.use_case.signup.SignupInputBoundary;
import client.use_case.signup.SignupInteractor;
import client.use_case.signup.SignupOutputBoundary;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.view.LoginView;

public class LoginUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private LoginUseCaseFactory() {
    }

    public static LoginView create(ViewManagerModel viewManagerModel,
                                   LoginViewModel loginViewModel,
                                   SignupViewModel signupViewModel,
                                   MainViewModel mainViewModel,
                                   ServerDataAccessObject serverDataAccessObject,
                                   HighContrastDataAccessInterface highContrastDataAccessObject) {
        LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, mainViewModel);
        UserFactory userFactory = new CommonUserFactory();
        SignupInputBoundary userSignupInteractor = new SignupInteractor(serverDataAccessObject, signupOutputBoundary, userFactory);
        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
        SignupController signupController = new SignupController(userSignupInteractor, passwordCheckerUseCaseInteractor);
        return new LoginView(loginController, signupController, loginViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
    }

    public static LoginController createLoginUseCase(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, ServerDataAccessObject serverDataAccessObject) {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
        return new LoginController(new LoginInteractor(serverDataAccessObject, loginOutputBoundary));
    }
}
