package client;

import client.entity.*;
import client.interface_adapter.Login.*;
import client.interface_adapter.Signup.*;
import client.interface_adapter.ViewManagerModel;
import client.use_case.Login.LoginInputBoundary;
import client.use_case.Login.LoginInteractor;
import client.use_case.Signup.*;
import client.view.*;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;

public class TestClientUI {

    public static void main(String[] args) {
        SignupDataAccessInterface userDataAccessObject = new SignupDataAccessInterface() {
            @Override
            public boolean existsByName(String username) {
                return false;
            }

            @Override
            public void save(User user) {
            }
        };
        SignupOutputBoundary outputBoundary = new SignupPresenter();
        UserFactory factory = new CommonUserFactory();
        SignupState signupState;
        SignupInputBoundary userSignupUseCaseInteractor = new SignupInteractor(userDataAccessObject, outputBoundary, factory);
        SignupController signupController = new SignupController(userSignupUseCaseInteractor);

        LoginInputBoundary userLoginUseCaseInteractor = new LoginInteractor();
        LoginController loginController = new LoginController(userLoginUseCaseInteractor);

        SmallJFrame app = new SmallJFrame("iKunnect Client");
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        //LoggedInViewModel loggedInViewModel = new LoggedInViewModel();

        SignupView signupView = new SignupView(signupController, signupViewModel);
        LoginView loginView = new LoginView(loginController, loginViewModel);
        //views.add(signupView);
        views.add(loginView);
        app.add(views);
        app.prepare();
        app.setSize(new Dimension(550, 500));
        /*while (true) {
            signupState = signupviewModel.getState();
            System.out.println("-------------------------------------------");
            System.out.println("username = " + signupState.getUsername());
            System.out.println("password = " + signupState.getPassword());
            System.out.println("passwordR = " + signupState.getRepeatPassword());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }*/
    }

}