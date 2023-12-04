import com.xiaoheizi.client.app.SignupUseCaseFactory;
import com.xiaoheizi.client.entity.UserFactory;
import com.xiaoheizi.client.interface_adapter.ViewManagerModel;
import com.xiaoheizi.client.interface_adapter.login.LoginViewModel;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.signup.SignupViewModel;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastOutputData;
import com.xiaoheizi.client.use_case.signup.*;
import com.xiaoheizi.client.view.SignupView;
import com.xiaoheizi.packet.PacketServerSignupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SignupInteractorTest {

    private SignupDataAccessInterface signupDataAccessInterface;
    private SignupOutputBoundary signupOutputBoundary;
    private UserFactory userFactory;
    private SignupInteractor signupInteractor;

    @BeforeEach
    void setUp() {
        signupDataAccessInterface = mock(SignupDataAccessInterface.class);
        signupOutputBoundary = mock(SignupOutputBoundary.class);
        userFactory = mock(UserFactory.class);

        signupInteractor = new SignupInteractor(signupDataAccessInterface, signupOutputBoundary, userFactory);
    }

    @Test
    void testExecute_PasswordsDoNotMatch() {
        SignupInputData inputData = new SignupInputData("username", "password", "differentPassword");

        signupInteractor.execute(inputData);

        verify(signupOutputBoundary).prepareFailView("Passwords don't match.");
    }

    @Test
    void testExecute_SignupSuccess() {
        SignupInputData inputData = new SignupInputData("username", "password", "password");
        PacketServerSignupResponse successResponse = new PacketServerSignupResponse(123, PacketServerSignupResponse.Status.SUCCESS);

        when(signupDataAccessInterface.signup("username", "password")).thenReturn(successResponse);

        signupInteractor.execute(inputData);

        verify(signupOutputBoundary).prepareSuccessView(any(SignupOutputData.class));
    }

    @Test
    void testExecute_SignupFail() {
        SignupInputData inputData = new SignupInputData("username", "password", "password");
        PacketServerSignupResponse failResponse = new PacketServerSignupResponse(123, PacketServerSignupResponse.Status.TOO_SHORT);

        when(signupDataAccessInterface.signup("username", "password")).thenReturn(failResponse);

        signupInteractor.execute(inputData);

        verify(signupOutputBoundary).prepareFailView(failResponse.toString());
    }
}

class SignupOutputDataTest {

    @Test
    void testUsernameIsCorrectlyStoredAndRetrieved() {
        String expectedUsername = "testUser";
        SignupOutputData data = new SignupOutputData(expectedUsername, "2022-01-01T00:00:00", -1);

        assertEquals(expectedUsername, data.getUsername(), "Username should match the one provided at construction");
    }

    @Test
    void testCreationTimeIsCorrectlyStoredAndRetrieved() {
        String expectedTime = "2022-01-01T00:00:00";
        SignupOutputData data = new SignupOutputData("testUser", expectedTime, -1);

        assertEquals(expectedTime, data.getCreationTime(), "Creation time should match the one provided at construction");
    }

    @Test
    void testCreationTimeCanBeModified() {
        String initialTime = "2022-01-01T00:00:00";
        String newTime = "2022-01-02T00:00:00";
        SignupOutputData data = new SignupOutputData("testUser", initialTime, -1);

        data.setCreationTime(newTime);

        assertEquals(newTime, data.getCreationTime(), "Creation time should be updated to the new value");
    }
}

class SignupUseCaseFactoryTest {

    @Test
    void testCreate() {
        ViewManagerModel viewManagerModel = mock(ViewManagerModel.class);
        MainViewModel mainViewModel = mock(MainViewModel.class);
        SignupViewModel signupViewModel = mock(SignupViewModel.class);
        LoginViewModel loginViewModel = mock(LoginViewModel.class);
        SignupDataAccessInterface userDataAccessObject = mock(SignupDataAccessInterface.class);
        HighContrastDataAccessInterface optionsDataAccessObject = mock(HighContrastDataAccessInterface.class);

        // Assuming HighContrastDataAccessInterface.HIGH_CONTRAST is a static final field
//        when(optionsDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST)).thenReturn(true);
        when(optionsDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST)).thenReturn(new HighContrastOutputData(true));

        SignupView result = SignupUseCaseFactory.create(viewManagerModel, mainViewModel, signupViewModel, loginViewModel, userDataAccessObject, optionsDataAccessObject);

        assertNotNull(result);
    }
}

