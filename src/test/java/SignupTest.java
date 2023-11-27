import client.entity.UserFactory;
import client.use_case.Signup.*;
import common.packet.PacketServerSignupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
