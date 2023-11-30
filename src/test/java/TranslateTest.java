import client.use_case.translate.TranslateDataAccessInterface;
import client.use_case.translate.TranslationInputData;
import client.use_case.translate.TranslationInteractor;
import client.use_case.translate.TranslationOutputData;
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

