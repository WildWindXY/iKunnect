package com.xiaoheizi.client.app;

import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.data_access.password_checker.PasswordCheckerDataAccess;
import com.xiaoheizi.client.entity.CommonUserFactory;
import com.xiaoheizi.client.entity.UserFactory;
import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.login.LoginController;
import com.xiaoheizi.client.interface_adapter.login.LoginPresenter;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.signup.SignupController;
import com.xiaoheizi.client.interface_adapter.signup.SignupPresenter;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.login.LoginInteractor;
import com.xiaoheizi.client.use_case.login.LoginOutputBoundary;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInputBoundary;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInteractor;
import com.xiaoheizi.client.use_case.signup.SignupInputBoundary;
import com.xiaoheizi.client.use_case.signup.SignupInteractor;
import com.xiaoheizi.client.use_case.signup.SignupOutputBoundary;
import com.xiaoheizi.client.view.LoginView;

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
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
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
