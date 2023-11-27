import client.app.LoginUseCaseFactory;
import client.data_access.ServerDataAccessObject;
import client.interface_adapter.Login.LoginController;
import client.interface_adapter.Login.LoginPresenter;
import client.interface_adapter.Login.LoginState;
import client.interface_adapter.Login.LoginViewModel;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.ViewManagerModel;
import client.use_case.Login.*;
import client.view.LoginView;
import common.packet.PacketServerLoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    void testExecuteSuccess() {
        String expectedUsername = "username";

        LoginInputData loginInputData = new LoginInputData("username", "password");
        PacketServerLoginResponse successResponse = new PacketServerLoginResponse(123, PacketServerLoginResponse.Status.SUCCESS);

        when(userDataAccessObject.login("username", "password")).thenReturn(successResponse);

        loginInteractor.execute(loginInputData);

        verify(loginPresenter).prepareSuccessView(any(LoginOutputData.class));

        LoginOutputData loginOutputData = new LoginOutputData("username", false);

        Assertions.assertEquals(loginOutputData.getUsername(), expectedUsername);
    }

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

    @BeforeEach
    public void setUp() {
        viewManagerModel = mock(ViewManagerModel.class);
        loginViewModel = mock(LoginViewModel.class);
        signupViewModel = mock(SignupViewModel.class);
        mainViewModel = mock(MainViewModel.class);
        serverDataAccessObject = mock(ServerDataAccessObject.class);
    }

    @Test
    public void testCreate() {
        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, mainViewModel, serverDataAccessObject);

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

    @Test
    void testPrepareSuccessView() {
        LoginOutputData response = new LoginOutputData("username", false);

        loginPresenter.prepareSuccessView(response);

        // Verify that the main view model's state is updated correctly
        // Here, you can capture the state passed to mainViewModel.setState() and perform assertions on it

        verify(viewManagerModel).setActiveView(mainViewModel.getViewName());
        verify(viewManagerModel).firePropertyChanged();
    }

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