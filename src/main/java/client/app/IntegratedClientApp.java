package client.app;

import client.data_access.*;
import client.data_access.options.OptionsDataAccess;
import client.data_access.receive_message.ReceiveMessageDataAccess;
import client.data_access.send_message.SendMessageDataAccess;
import client.data_access.translate.TranslateDataAccess;
import client.data_access.user_data.FileUserDataAccessObject;
import client.entity.*;
import client.interface_adapter.logged_in.LoggedInViewModel;
import client.interface_adapter.login.*;
import client.interface_adapter.main.*;
import client.interface_adapter.signup.*;
import client.interface_adapter.ViewManagerModel;
import client.view.*;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static utils.MessageEncryptionUtils.initKey;

public class IntegratedClientApp {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");
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

        FileUserDataAccessObject fileUserDataAccessObject;
        try {
            fileUserDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        OptionsDataAccess optionsDataAccessObject = new OptionsDataAccess();

        // Create and add your views to the card layout
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, fileUserDataAccessObject);
        signupView.setPreferredSize(new Dimension(550,500));
        views.add(signupView, signupView.VIEW_NAME);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, fileUserDataAccessObject);
        loginView.setPreferredSize(new Dimension(550,500));
        views.add(loginView, loginView.VIEW_NAME);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel);
        views.add(loggedInView, loggedInView.VIEW_NAME);

        MainView mainView = MainUseCaseFactory.create(loginViewModel.getState().getUsername(), sendDataAccessObject, receiveDataAccessObject, translateDataAccessObject, optionsDataAccessObject, mainViewModel);
        mainView.setPreferredSize(new Dimension(1200, 800)); // Set the preferred size
        views.add(mainView, mainView.VIEW_NAME);


        // Set the initial active view
        viewManagerModel.setActiveView(loginView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();

        // Add the views to your JFrame and configure the JFrame
        app.add(views);
        app.pack();
        app.init();
        //app.setSize(new Dimension(550, 500));
        app.setSize(new Dimension(1200, 800));
        app.setResizable(false);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}
