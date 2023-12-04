package com.xiaoheizi.client.app;

import com.xiaoheizi.client.data_access.password_checker.PasswordCheckerDataAccess;
import com.xiaoheizi.client.entity.CommonUserFactory;
import com.xiaoheizi.client.entity.UserFactory;
import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.signup.SignupController;
import com.xiaoheizi.client.interface_adapter.signup.SignupPresenter;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInputBoundary;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInteractor;
import com.xiaoheizi.client.use_case.signup.SignupDataAccessInterface;
import com.xiaoheizi.client.use_case.signup.SignupInputBoundary;
import com.xiaoheizi.client.use_case.signup.SignupInteractor;
import com.xiaoheizi.client.use_case.signup.SignupOutputBoundary;
import com.xiaoheizi.client.view.SignupView;

import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private SignupUseCaseFactory() {
    }

    public static SignupView create(ViewManagerModel viewManagerModel, MainViewModel mainViewModel, SignupViewModel signupViewModel, LoginViewModel loginViewModel, SignupDataAccessInterface userDataAccessObject, HighContrastDataAccessInterface optionsDataAccessObject) {

        try {
            SignupController signupController = createUserSignupUseCase(viewManagerModel, signupViewModel, mainViewModel, loginViewModel, userDataAccessObject);
            return new SignupView(signupController, signupViewModel, optionsDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel, MainViewModel mainViewModel, LoginViewModel loginViewModel, SignupDataAccessInterface userDataAccessObject) throws IOException {
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);

        UserFactory userFactory = new CommonUserFactory();

        SignupInputBoundary userSignupInteractor = new SignupInteractor(userDataAccessObject, signupOutputBoundary, userFactory);

        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
        return new SignupController(userSignupInteractor, passwordCheckerUseCaseInteractor);
    }
}
