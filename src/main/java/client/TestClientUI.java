package client;

import client.data_access.password_checker.PasswordCheckerDataAccess;

import client.entity.*;
import client.interface_adapter.Logged_in.LoggedInViewModel;
import client.interface_adapter.Login.*;
import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.*;
import client.interface_adapter.ViewManagerModel;

import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.view.*;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
        SignupController signupController = new SignupController(userSignupUseCaseInteractor,passwordCheckerUseCaseInteractor);

        LoginDataAccessInterface loginDataAccessInterface = new LoginDataAccessInterface() {
            @Override
            public boolean existsByName(String username) {
                return false;
            }

            @Override
            public void save(User user) {
            }

            @Override
            public User get(String username) {
                return null;
            }
        };
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter();
        LoginInputBoundary userLoginUseCaseInteractor = new LoginInteractor(loginDataAccessInterface, loginOutputBoundary);
        LoginController loginController = new LoginController(userLoginUseCaseInteractor);


        SmallJFrame app = new SmallJFrame("iKunnect Client");
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        MainViewModel mainViewModel = new MainViewModel();

        FileUserDataAccessObject fileUserDataAccessObject;
        try {
            fileUserDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String serverAddress = "localhost";
        int serverPort = 0x2304;
        ServerDataAccessObject serverDataAccessObject = new ServerDataAccessObject(serverAddress, serverPort);
        SendMessageDataAccess sendDataAccessObject = new SendMessageDataAccess(serverDataAccessObject);
        ReceiveMessageDataAccess receiveDataAccessObject = new ReceiveMessageDataAccess(serverDataAccessObject);
        TranslateDataAccess translateDataAccessObject = new TranslateDataAccess();


        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, fileUserDataAccessObject);
        views.add(signupView, signupView.VIEW_NAME);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, fileUserDataAccessObject);
        views.add(loginView, loginView.VIEW_NAME);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        views.add(loggedInView, loggedInView.VIEW_NAME);

        MainView mainView = MainUseCaseFactory.create("", sendDataAccessObject, receiveDataAccessObject, translateDataAccessObject, mainViewModel);
        views.add(mainView, mainView.VIEW_NAME);

        viewManagerModel.setActiveView(signupView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();

        app.setVisible(true);


        SignupView signupView = new SignupView(signupController, signupViewModel);
        LoginView loginView = new LoginView(loginController,signupController, loginViewModel);
        //views.add(signupView);
        views.add(loginView);

        app.add(views);
        app.pack();
        app.prepare();
        app.setSize(new Dimension(1200, 800));
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