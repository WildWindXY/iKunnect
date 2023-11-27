import client.use_case.PasswordChecker.PasswordCheckerDataAccessInterface;
import client.use_case.PasswordChecker.PasswordCheckerInputData;
import client.use_case.PasswordChecker.PasswordCheckerInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PasswordCheckerInteractorTest {

    private PasswordCheckerDataAccessInterface dataAccess;
    private PasswordCheckerInteractor passwordCheckerInteractor;

    @BeforeEach
    void setUp() {
        dataAccess = mock(PasswordCheckerDataAccessInterface.class);
        passwordCheckerInteractor = new PasswordCheckerInteractor(dataAccess);
    }

    @Test
    void testExecute_ValidPassword() {
        String validPassword = "ValidPassword123";
        when(dataAccess.isPasswordValid(validPassword)).thenReturn(true);

        PasswordCheckerInputData inputData = new PasswordCheckerInputData(validPassword);
        boolean result = passwordCheckerInteractor.execute(inputData);

        assertTrue(result);
    }

    @Test
    void testExecute_InvalidPassword() {
        String invalidPassword = "short";
        when(dataAccess.isPasswordValid(invalidPassword)).thenReturn(false);

        PasswordCheckerInputData inputData = new PasswordCheckerInputData(invalidPassword);
        boolean result = passwordCheckerInteractor.execute(inputData);

        assertFalse(result);
    }
}
