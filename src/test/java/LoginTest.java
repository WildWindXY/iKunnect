import client.app.LoginUseCaseFactory;
import client.data_access.ServerDataAccessObject;
import client.data_access.high_contrast.HighContrastDataAccess;
import client.interface_adapter.ViewManagerModel;
import client.interface_adapter.login.LoginController;
import client.interface_adapter.login.LoginPresenter;
import client.interface_adapter.login.LoginViewModel;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.signup.SignupViewModel;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.login.*;
import client.view.LoginView;
import common.packet.PacketServerGetFriendListResponse;
import common.packet.PacketServerLoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Triple;
import utils.Tuple;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

class LoginInteractorTest {

    private LoginDataAccessInterface userDataAccessObject;
    private LoginOutputBoundary loginPresenter;
    private LoginInteractor loginInteractor;

    @BeforeEach
    void setUp() {
        userDataAccessObject = mock(LoginDataAccessInterface.class);
        loginPresenter = mock(LoginOutputBoundary.class);
        loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
    }

    @Test
    void testSuccessfulLogin() {
        LoginInputData inputData = new LoginInputData("username", "password");
        PacketServerLoginResponse loginResponse = new PacketServerLoginResponse(123, PacketServerLoginResponse.Status.SUCCESS);
        HashMap<Integer, Tuple<String, Integer>> friends = new HashMap<>();
        HashMap<Integer, List<Triple<Long, Integer, String>>> chats = new HashMap<>();
        PacketServerGetFriendListResponse friendListResponse = new PacketServerGetFriendListResponse(friends, chats, PacketServerGetFriendListResponse.Status.SUCCESS);

        when(userDataAccessObject.login(inputData.getUsername(), inputData.getPassword())).thenReturn(loginResponse);
        when(userDataAccessObject.getFriendList()).thenReturn(friendListResponse);

        loginInteractor.execute(inputData);

        verify(loginPresenter).prepareSuccessView(any(LoginOutputData.class));
    }


    //TODO: broken due to LoginOutputData changes
//    @Test
//    void testExecuteSuccess() {
//        String expectedUsername = "username";
//
//        LoginInputData loginInputData = new LoginInputData("username", "password");
//        PacketServerLoginResponse successResponse = new PacketServerLoginResponse(123, PacketServerLoginResponse.Status.SUCCESS);
//
//        when(userDataAccessObject.login("username", "password")).thenReturn(successResponse);
//
//        loginInteractor.execute(loginInputData);
//
//        verify(loginPresenter).prepareSuccessView(any(LoginOutputData.class));
//
//        LoginOutputData loginOutputData = new LoginOutputData("username", false);
//
//        Assertions.assertEquals(loginOutputData.getUsername(), expectedUsername);
//    }

    @Test
    void testExecuteFailure() {
        LoginInputData loginInputData = new LoginInputData("username", "wrongPassword");
        PacketServerLoginResponse failureResponse = new PacketServerLoginResponse(123, PacketServerLoginResponse.Status.WRONG_PASSWORD);

        when(userDataAccessObject.login("username", "wrongPassword")).thenReturn(failureResponse);

        loginInteractor.execute(loginInputData);

        verify(loginPresenter).prepareFailView(failureResponse.toString());
    }

    @Test
    void testExecuteSignup() {
        loginInteractor.executeSignup();

        verify(loginPresenter).prepareSignupView();
    }
}


class LoginControllerTest {

    private LoginInputBoundary loginUseCaseInteractor;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        // Mocking the LoginInputBoundary interface
        loginUseCaseInteractor = mock(LoginInputBoundary.class);
        loginController = new LoginController(loginUseCaseInteractor);
    }

    @Test
    void testExecute() {
        String username = "testUser";
        String password = "testPass";

        loginController.execute(username, password);

        // Verify that execute method is called with the correct LoginInputData
        verify(loginUseCaseInteractor).execute(refEq(new LoginInputData(username, password)));
    }

    @Test
    void testExecuteSignup() {
        loginController.executeSignup();

        // Verify that executeSignup method is called
        verify(loginUseCaseInteractor).executeSignup();
    }
}

class LoginUseCaseFactoryTest {

    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private SignupViewModel signupViewModel;
    private MainViewModel mainViewModel;
    private ServerDataAccessObject serverDataAccessObject;
    private HighContrastDataAccessInterface highContrastDataAccessObject;

    @BeforeEach
    public void setUp() {
        viewManagerModel = mock(ViewManagerModel.class);
        loginViewModel = mock(LoginViewModel.class);
        signupViewModel = mock(SignupViewModel.class);
        mainViewModel = mock(MainViewModel.class);
        serverDataAccessObject = mock(ServerDataAccessObject.class);
//        highContrastDataAccessObject = mock(HighContrastDataAccess.class);
//        viewManagerModel = new ViewManagerModel();
//        loginViewModel = new LoginViewModel();
//        signupViewModel = new SignupViewModel();
//        mainViewModel = new MainViewModel();
//        serverDataAccessObject = new ServerDataAccessObject("localhost", 1233);
        highContrastDataAccessObject = new HighContrastDataAccess();

    }

    @Test
    public void testCreate() {
        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject, highContrastDataAccessObject);

        Assertions.assertNotNull(loginView);
        // Additional assertions to check if the created objects are properly initialized
    }

    @Test
    public void testCreateLoginUseCase() {
        LoginController loginController = LoginUseCaseFactory.createLoginUseCase(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);

        Assertions.assertNotNull(loginController);
        // Additional assertions to check if the created objects are properly initialized
    }

    // Additional tests to verify other aspects of the LoginUseCaseFactory behavior
}

class LoginPresenterTest {

    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private SignupViewModel signupViewModel;
    private MainViewModel mainViewModel;
    private LoginPresenter loginPresenter;

    @BeforeEach
    void setUp() {
        viewManagerModel = mock(ViewManagerModel.class);
        loginViewModel = mock(LoginViewModel.class);
        signupViewModel = mock(SignupViewModel.class);
        mainViewModel = mock(MainViewModel.class);

        loginPresenter = new LoginPresenter(viewManagerModel, signupViewModel, mainViewModel, loginViewModel);
    }

    //TODO: broken due to LoginOutputData changes
//    @Test
//    void testPrepareSuccessView() {
//        LoginOutputData response = new LoginOutputData("username", false);
//
//        loginPresenter.prepareSuccessView(response);
//
//        // Verify that the main view model's state is updated correctly
//        // Here, you can capture the state passed to mainViewModel.setState() and perform assertions on it
//
//        verify(viewManagerModel).setActiveView(mainViewModel.getViewName());
//        verify(viewManagerModel).firePropertyChanged();
//    }

//    @Test
//    void testPrepareFailView() {
//        String error = "Invalid password";
//
//        loginPresenter.prepareFailView(error);
//
//        // Verify that the login view model's state is updated with the error message
//        // Here, you can capture the state passed to loginViewModel.firePropertyChanged() and perform assertions on it
//
//        ArgumentCaptor<LoginState> captor = ArgumentCaptor.forClass(LoginState.class);
//        verify(loginViewModel).firePropertyChanged();
//        // Other assertions as needed
//    }

    @Test
    void testPrepareSignupView() {
        loginPresenter.prepareSignupView();

        verify(viewManagerModel).setActiveView(signupViewModel.getViewName());
        verify(viewManagerModel).firePropertyChanged();
    }
}