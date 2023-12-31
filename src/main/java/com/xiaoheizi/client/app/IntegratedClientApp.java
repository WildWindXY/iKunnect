package com.xiaoheizi.client.app;

import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.data_access.add_friend.AddFriendDataAccess;
import com.xiaoheizi.client.data_access.high_contrast.HighContrastDataAccess;
import com.xiaoheizi.client.data_access.receive_message.ReceiveMessageDataAccess;
import com.xiaoheizi.client.data_access.send_message.SendMessageDataAccess;
import com.xiaoheizi.client.data_access.translate.TranslateDataAccess;
import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.view.LoginView;
import com.xiaoheizi.client.view.MainView;
import com.xiaoheizi.client.view.SignupView;
import com.xiaoheizi.client.view.ViewManager;
import com.xiaoheizi.client.view.components.frames.SmallJFrame;

import javax.swing.*;
import java.awt.*;

import static com.xiaoheizi.utils.MessageEncryptionUtils.initKey;

public class IntegratedClientApp {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.2");
        // Initialize your JFrame and CardLayout
        SmallJFrame app = new SmallJFrame("Integrated Client App");
        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Initialize view models and data access objects
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        MainViewModel mainViewModel = new MainViewModel();

//        String serverAddress = "154.64.228.76";
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
        HighContrastDataAccess highContrastDataAccessObject = new HighContrastDataAccess();
        AddFriendDataAccess addFriendDataAccessObject = new AddFriendDataAccess(serverDataAccessObject);

        // Create and add your views to the card layout
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, mainViewModel, signupViewModel, loginViewModel, serverDataAccessObject, highContrastDataAccessObject);
        //signupView.setPreferredSize(new Dimension(550, 500));
        views.add(signupView, SignupView.VIEW_NAME);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject, highContrastDataAccessObject);
        //loginView.setPreferredSize(new Dimension(550, 500));
        views.add(loginView, LoginView.VIEW_NAME);


        MainView mainView = MainUseCaseFactory.create(loginViewModel.getState().getUsername(), sendDataAccessObject, receiveDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, mainViewModel);
        mainView.setPreferredSize(new Dimension(1200, 800)); // Set the preferred size
        views.add(mainView, MainView.VIEW_NAME);


        // Set the initial active view
        viewManagerModel.setActiveView(LoginView.VIEW_NAME);
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
