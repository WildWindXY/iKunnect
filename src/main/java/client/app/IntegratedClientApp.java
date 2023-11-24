package client.app;

import client.data_access.ServerDataAccessObject;
import client.data_access.receive_message.ReceiveMessageDataAccess;
import client.data_access.send_message.SendMessageDataAccess;
import client.data_access.translate.TranslateDataAccess;
import client.interface_adapter.Logged_in.LoggedInViewModel;
import client.interface_adapter.Login.LoginViewModel;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.view.*;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;

import static utils.MessageEncryptionUtils.initKey;

public class IntegratedClientApp {

    public static void main(String[] args) {
        // Initialize your JFrame and CardLayout
        SmallJFrame app = new SmallJFrame("Integrated Client App");
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Initialize view models and data access objects
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        MainViewModel mainViewModel = new MainViewModel();

        String serverAddress = "localhost";
        int serverPort = 0x2304;
        try {
            initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ServerDataAccessObject serverDataAccessObject = new ServerDataAccessObject(serverAddress, serverPort);
        SendMessageDataAccess sendDataAccessObject = new SendMessageDataAccess(serverDataAccessObject);
        ReceiveMessageDataAccess receiveDataAccessObject = new ReceiveMessageDataAccess(serverDataAccessObject);
        TranslateDataAccess translateDataAccessObject = new TranslateDataAccess();

        // Create and add your views to the card layout
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, serverDataAccessObject);
        signupView.setPreferredSize(new Dimension(550, 500));
        views.add(signupView, SignupView.VIEW_NAME);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);
        loginView.setPreferredSize(new Dimension(550, 500));
        views.add(loginView, LoginView.VIEW_NAME);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        views.add(loggedInView, LoggedInView.VIEW_NAME);

        MainView mainView = MainUseCaseFactory.create(loginViewModel.getState().getUsername(), sendDataAccessObject, receiveDataAccessObject, translateDataAccessObject, mainViewModel);
        mainView.setPreferredSize(new Dimension(1200, 800)); // Set the preferred size
        views.add(mainView, MainView.VIEW_NAME);


        // Set the initial active view
        viewManagerModel.setActiveView(LoginView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();

        // Add the views to your JFrame and configure the JFrame
        app.add(views);
        app.pack();
        app.prepare();
//        app.setSize(new Dimension(550, 500));
        app.setSize(new Dimension(1200, 800));
        app.setVisible(true);
    }
}
