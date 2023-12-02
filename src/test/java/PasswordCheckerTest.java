import client.data_access.password_checker.PasswordCheckerDataAccess;
import client.use_case.password_checker.PasswordCheckerDataAccessInterface;
import client.use_case.password_checker.PasswordCheckerInputData;
import client.use_case.password_checker.PasswordCheckerInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

class PasswordCheckerDataAccessTest {

    @Test
    void testIsPasswordValid() throws Exception {
        // Mock the PasswordCheck class
        PasswordCheckerDataAccess.PasswordCheck mockPasswordCheck = mock(PasswordCheckerDataAccess.PasswordCheck.class);
        String testPassword = "password123";
        String testSha1 = "5BAA61E4C9B93F3F0682250B6CF8331B7EE68FD8"; // SHA1 for "password"
        String hashPrefix = testSha1.substring(0, 5);
        String suffix = testSha1.substring(5).toUpperCase();

//        when(mockPasswordCheck.toSHA1(testPassword)).thenReturn(testSha1);
//        when(mockPasswordCheck.checkPwnedAPI(hashPrefix)).thenReturn(suffix + ":10");

        PasswordCheckerDataAccess passwordCheckerDataAccess = new PasswordCheckerDataAccess();

        boolean result = passwordCheckerDataAccess.isPasswordValid(testPassword);

        assertFalse(result); // Assuming the password is found in the API and is not valid
    }
}