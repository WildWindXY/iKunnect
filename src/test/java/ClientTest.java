import client.data_access.ServerDataAccessObject;
import client.data_access.add_friend.AddFriendDataAccess;
import client.data_access.high_contrast.HighContrastDataAccess;
import client.data_access.password_checker.PasswordCheckerDataAccess;
import client.data_access.receive_message.ReceiveMessageDataAccess;
import client.data_access.send_message.SendMessageDataAccess;
import client.data_access.translate.TranslateDataAccess;
import client.entity.CommonUserFactory;
import client.entity.User;
import client.entity.UserFactory;
import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.add_friend.AddFriendPresenter;
import client.interface_adapter.login.LoginController;
import client.interface_adapter.login.LoginPresenter;
import client.interface_adapter.login.LoginViewModel;
import client.interface_adapter.main.MainController;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.options.HighContrastPresenter;
import client.interface_adapter.receive_message.ReceiveMessagePresenter;
import client.interface_adapter.send_message.SendMessagePresenter;
import client.interface_adapter.signup.SignupController;
import client.interface_adapter.signup.SignupPresenter;
import client.interface_adapter.signup.SignupViewModel;
import client.use_case.add_friend.AddFriendInputBoundary;
import client.use_case.add_friend.AddFriendInteractor;
import client.use_case.add_friend.AddFriendOutputBoundary;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.high_contrast.HighContrastInputBoundary;
import client.use_case.high_contrast.HighContrastInteractor;
import client.use_case.high_contrast.HighContrastOutputBoundary;
import client.use_case.login.LoginInteractor;
import client.use_case.login.LoginOutputBoundary;
import client.use_case.login.LoginOutputData;
import client.use_case.password_checker.PasswordCheckerInputBoundary;
import client.use_case.password_checker.PasswordCheckerInteractor;
import client.use_case.receive_message.ReceiveMessageInputBoundary;
import client.use_case.receive_message.ReceiveMessageInteractor;
import client.use_case.receive_message.ReceiveMessageOutputBoundary;
import client.use_case.send_message.SendMessageInputBoundary;
import client.use_case.send_message.SendMessageInteractor;
import client.use_case.send_message.SendMessageOutputBoundary;
import client.use_case.send_message.SendMessageOutputData;
import client.use_case.signup.SignupInputBoundary;
import client.use_case.signup.SignupInteractor;
import client.use_case.signup.SignupOutputBoundary;
import client.use_case.signup.SignupOutputData;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInteractor;
import client.view.LoginView;
import client.view.MainView;
import client.view.SignupView;
import client.view.ViewManager;
import client.view.components.frames.SmallJFrame;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class ClientTest {
    @Test
    public void testClient() {
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

        String serverAddress = "localhost";
        int serverPort = 0x2304;

        ServerDataAccessObject serverDataAccessObject = new ServerDataAccessObject(serverAddress, serverPort);
        SendMessageDataAccess sendDataAccessObject = new SendMessageDataAccess(serverDataAccessObject);
        ReceiveMessageDataAccess receiveDataAccessObject = new ReceiveMessageDataAccess(serverDataAccessObject);
        TranslateDataAccess translateDataAccessObject = new TranslateDataAccess();
        HighContrastDataAccess highContrastDataAccessObject = new HighContrastDataAccess();
        AddFriendDataAccess addFriendDataAccessObject = new AddFriendDataAccess(serverDataAccessObject);

        //Create SignupView
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
        UserFactory userFactory = new CommonUserFactory();
        SignupInputBoundary userSignupInteractor = new SignupInteractor(serverDataAccessObject, signupOutputBoundary, userFactory);
        PasswordCheckerInputBoundary passwordCheckerUseCaseInteractor = new PasswordCheckerInteractor(new PasswordCheckerDataAccess());
        SignupController signupController = new SignupController(userSignupInteractor, passwordCheckerUseCaseInteractor);
        SignupView signupView = new SignupView(signupController, signupViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));

        views.add(signupView, SignupView.VIEW_NAME);

        //Create LoginView
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
        LoginController loginController = new LoginController(new LoginInteractor(serverDataAccessObject, loginOutputBoundary));
        LoginView loginView = new LoginView(loginController, signupController, loginViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
        views.add(loginView, LoginView.VIEW_NAME);

        //Create MainView
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        HighContrastOutputBoundary highContrastOutputBoundary = new HighContrastPresenter(mainViewModel);
        AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveDataAccessObject, receiveMessageOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        HighContrastInputBoundary highContrastInteractor = new HighContrastInteractor(highContrastDataAccessObject, highContrastOutputBoundary);
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(addFriendDataAccessObject, addFriendOutputBoundary);
        MainController mainController = new MainController(loginViewModel.getState().getUsername(), sendMessageInteractor, receiveMessageInteractor, translationInteractor, highContrastInteractor, addFriendInteractor);
        MainView mainView = new MainView(mainController, mainViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
        mainView.setPreferredSize(new Dimension(1200, 800));
        views.add(mainView, MainView.VIEW_NAME);

        viewManagerModel.setActiveView(LoginView.VIEW_NAME);
        viewManagerModel.firePropertyChanged();

        app.add(views);
        app.pack();
        app.init();
        app.setSize(new Dimension(1200, 800));
        app.setResizable(false);
        app.setLocationRelativeTo(null);

        //Begin testing
        testRunnable(() -> loginController.execute("user1", "user1user"));
        testRunnable(() -> signupController.execute("user1", "user1user", "user1user"));
        testRunnable(mainController::getFriendList);
        testRunnable(() -> mainController.translateMessage("hello"));
        testRunnable(mainController::openOptionsMenu);
        testRunnable(() -> highContrastInteractor.execute(0));
        testRunnable(() -> addFriendInteractor.execute("user"));
        testRunnable(() -> loginOutputBoundary.prepareFailView("password"));
        testRunnable(() -> loginOutputBoundary.prepareFailView("failed"));
        testRunnable(() -> loginOutputBoundary.prepareSuccessView(new LoginOutputData(null, null, null, 0)));

        testRunnable(addFriendOutputBoundary::prepareSuccessView);
        testRunnable(mainController::openOptionsMenu);
        testRunnable(() -> sendMessageOutputBoundary.presentSendMessageResult(new SendMessageOutputData(true, -1L, null), null));
        testRunnable(signupController::executeLogin);
        testRunnable(() -> signupOutputBoundary.prepareSuccessView(new SignupOutputData(null, "2022-01-01T00:00:00", 0)));
        testRunnable(signupOutputBoundary::prepareLoginView);
        testRunnable(() -> signupOutputBoundary.prepareFailView("failed"));
        testRunnable(() -> IKunnect.main(null));

        //Test Entities
        CommonUserFactory commonUserFactory = new CommonUserFactory();
        User user = commonUserFactory.create("name", "password", null);
        assert (user.getName().equals("name"));
        assert (user.getPassword().equals("password"));
        assert (user.getCreationTime() == null);
    }

    private void testRunnable(Runnable runnable) {
        try {
            Thread thread = new Thread(runnable);
            thread.start();
            Thread.sleep(100);
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
