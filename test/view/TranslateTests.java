import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.usecase.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ChatSystemTest {

    @Mock
    private TranslationService translationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessageWithoutTranslation() {
        ChatSystem chatSystem = new ChatSystem(translationService);

        String message = "Hello, how are you?";
        TranslationResponse translationResponse = new TranslationResponse(message);

        Mockito.when(translationService.translate(any(TranslationRequest.class)))
                .thenReturn(translationResponse);

        String sentMessage = chatSystem.sendMessage("User1", "User2", message);
        assertEquals(message, sentMessage);
    }

    @Test
    public void testSendMessageWithTranslation() {
        ChatSystem chatSystem = new ChatSystem(translationService);

        String originalMessage = "Hello, how are you?";
        String translatedMessage = "Bonjour, comment ça va?";
        TranslationResponse translationResponse = new TranslationResponse(translatedMessage);

        Mockito.when(translationService.translate(any(TranslationRequest.class)))
                .thenReturn(translationResponse);

        String sentMessage = chatSystem.sendMessage("User1", "User2", originalMessage);
        assertEquals(translatedMessage, sentMessage);
    }

    @Test
    public void testReceiveMessage() {
        ChatSystem chatSystem = new ChatSystem(translationService);

        String message = "Hello, how are you?";
        TranslationResponse translationResponse = new TranslationResponse(message);

        Mockito.when(translationService.translate(any(TranslationRequest.class)))
                .thenReturn(translationResponse);

        chatSystem.sendMessage("User1", "User2", message);
        String receivedMessage = chatSystem.receiveMessage("User2", "User1");
        assertEquals(message, receivedMessage);
    }
}
