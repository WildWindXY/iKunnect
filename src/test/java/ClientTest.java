import com.xiaoheizi.IKunnect;
import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.data_access.add_friend.AddFriendDataAccess;
import com.xiaoheizi.client.data_access.high_contrast.HighContrastDataAccess;
import com.xiaoheizi.client.data_access.password_checker.PasswordCheckerDataAccess;
import com.xiaoheizi.client.data_access.receive_message.ReceiveMessageDataAccess;
import com.xiaoheizi.client.data_access.send_message.SendMessageDataAccess;
import com.xiaoheizi.client.data_access.translate.TranslateDataAccess;
import com.xiaoheizi.client.entity.CommonUserFactory;
import com.xiaoheizi.client.entity.User;
import com.xiaoheizi.client.entity.UserFactory;
import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.add_friend.AddFriendPresenter;
import com.xiaoheizi.client.interface_adapter.login.LoginController;
import com.xiaoheizi.client.interface_adapter.login.LoginPresenter;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainController;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.options.HighContrastPresenter;
import com.xiaoheizi.client.interface_adapter.receive_message.ReceiveMessagePresenter;
import com.xiaoheizi.client.interface_adapter.send_message.SendMessagePresenter;
import com.xiaoheizi.client.interface_adapter.signup.SignupController;
import com.xiaoheizi.client.interface_adapter.signup.SignupPresenter;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.use_case.add_friend.AddFriendInputBoundary;
import com.xiaoheizi.client.use_case.add_friend.AddFriendInteractor;
import com.xiaoheizi.client.use_case.add_friend.AddFriendOutputBoundary;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastInputBoundary;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastInteractor;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastOutputBoundary;
import com.xiaoheizi.client.use_case.login.LoginInteractor;
import com.xiaoheizi.client.use_case.login.LoginOutputBoundary;
import com.xiaoheizi.client.use_case.login.LoginOutputData;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInputBoundary;
import com.xiaoheizi.client.use_case.password_checker.PasswordCheckerInteractor;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageInputBoundary;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageInteractor;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputBoundary;
import com.xiaoheizi.client.use_case.send_message.SendMessageInputBoundary;
import com.xiaoheizi.client.use_case.send_message.SendMessageInteractor;
import com.xiaoheizi.client.use_case.send_message.SendMessageOutputBoundary;
import com.xiaoheizi.client.use_case.send_message.SendMessageOutputData;
import com.xiaoheizi.client.use_case.signup.SignupInputBoundary;
import com.xiaoheizi.client.use_case.signup.SignupInteractor;
import com.xiaoheizi.client.use_case.signup.SignupOutputBoundary;
import com.xiaoheizi.client.use_case.signup.SignupOutputData;
import com.xiaoheizi.client.use_case.translate.TranslationInputBoundary;
import com.xiaoheizi.client.use_case.translate.TranslationInteractor;
import com.xiaoheizi.client.view.LoginView;
import com.xiaoheizi.client.view.MainView;
import com.xiaoheizi.client.view.SignupView;
import com.xiaoheizi.client.view.ViewManager;
import com.xiaoheizi.client.view.components.frames.SmallJFrame;
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
