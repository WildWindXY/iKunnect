import com.xiaoheizi.client.data_access.translate.TranslateDataAccess;
import com.xiaoheizi.client.entity.TranslationResponse;
import com.xiaoheizi.client.use_case.translate.TranslateDataAccessInterface;
import com.xiaoheizi.client.use_case.translate.TranslationInputData;
import com.xiaoheizi.client.use_case.translate.TranslationInteractor;
import com.xiaoheizi.client.use_case.translate.TranslationOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TranslationInteractorTest {

    private TranslateDataAccessInterface translationDataAccessObject;
    private TranslationInteractor translationInteractor;

    @BeforeEach
    void setUp() {
        // Mocking the TranslateDataAccessInterface
        translationDataAccessObject = mock(TranslateDataAccessInterface.class);
        translationInteractor = new TranslationInteractor(translationDataAccessObject);
    }

    @Test
    void testExecute() {
        String inputText = "Hello";
        String translatedText = "Bonjour";
        TranslationInputData inputData = new TranslationInputData(inputText);
        TranslationOutputData outputData = new TranslationOutputData(translatedText, "fr");

        // Configure the mock to return a specific value when a method is called
        when(translationDataAccessObject.translate(inputText)).thenReturn(outputData);

        // Execute the method to be tested
        String result = translationInteractor.execute(inputData);

        // Verify that the correct methods were called on the mock
        verify(translationDataAccessObject).translate(inputText);

        // Assert that the result is as expected
        assertEquals(translatedText, result, "The translated text does not match the expected value");
    }
}

class TranslateDataAccessTest {

    private TranslateDataAccess translateDataAccess;

    @BeforeEach
    void setUp() {
        // Assuming DeeplAPI can be mocked or its dependencies can be injected
//        TranslateDataAccess.DeeplAPI mockDeeplAPI = mock(TranslateDataAccess.DeeplAPI.class);
        translateDataAccess = new TranslateDataAccess();
    }

    @Test
    void testTranslate() {
        String translatedText = "Hello, World!";
        String testText = "Bonjour, Monde!";
        String detectedLanguage = "FR";

        // Mock the response from DeeplAPI
        TranslationResponse mockResponse = new TranslationResponse(translatedText, detectedLanguage);
//        when(mockDeeplAPI.translate(any(TranslationRequest.class))).thenReturn(mockResponse);

        // Execute the method under test
        TranslationOutputData outputData = translateDataAccess.translate(testText);

        // Assertions
        assertEquals(translatedText, outputData.getTranslatedText());
        assertEquals(detectedLanguage, outputData.getDetectedLanguage());
    }
}