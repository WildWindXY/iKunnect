package client;

import client.app.LoginUseCaseFactory;
import client.app.MainUseCaseFactory;
import client.app.SignupUseCaseFactory;
import client.data_access.ServerDataAccessObject;
import client.data_access.receive_message.ReceiveMessageDataAccess;
import client.data_access.send_message.SendMessageDataAccess;
import client.data_access.translate.TranslateDataAccess;
import client.data_access.user_data.FileUserDataAccessObject;
import client.entity.CommonUserFactory;
import client.interface_adapter.Logged_in.LoggedInViewModel;
import client.interface_adapter.Login.LoginViewModel;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.view.*;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TestClientUI {

    public static void main(String[] args) {

//        SignupDataAccessInterface userDataAccessObject = new SignupDataAccessInterface() {
//            @Override
//            public boolean existsByName(String username) {
//                return false;
//            }
//
//            @Override
//            public void save(User user) {
//            }
//        };
//        SignupOutputBoundary outputBoundary = new SignupPresenter();
//        UserFactory factory = new CommonUserFactory();
//        SignupState signupState;
//        SignupInputBoundary userSignupUseCaseInteractor = new SignupInteractor(userDataAccessObject, outputBoundary, factory);
//
//        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
//        SignupController signupController = new SignupController(userSignupUseCaseInteractor,passwordCheckerUseCaseInteractor);

//        LoginDataAccessInterface loginDataAccessInterface = new LoginDataAccessInterface() {
//            @Override
//            public boolean existsByName(String username) {
//                return false;
//            }
//
//            @Override
//            public void save(User user) {
//            }
//
//            @Override
//            public User get(String username) {
//                return null;
//            }
//        };
//        LoginOutputBoundary loginOutputBoundary = new LoginPresenter();
//        LoginInputBoundary userLoginUseCaseInteractor = new LoginInteractor(loginDataAccessInterface, loginOutputBoundary);
//        LoginController loginController = new LoginController(userLoginUseCaseInteractor);


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


        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, serverDataAccessObject);
        views.add(signupView, SignupView.VIEW_NAME);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);
        views.add(loginView, LoginView.VIEW_NAME);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        views.add(loggedInView, LoggedInView.VIEW_NAME);

        MainView mainView = MainUseCaseFactory.create("", sendDataAccessObject, receiveDataAccessObject, translateDataAccessObject, mainViewModel);
        views.add(mainView, MainView.VIEW_NAME);

        viewManagerModel.setActiveView(SignupView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();

        app.setVisible(true);

//
//        SignupView signupView = new SignupView(signupController, signupViewModel);
//        LoginView loginView = new LoginView(loginController,signupController, loginViewModel);
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