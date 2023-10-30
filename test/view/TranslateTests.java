package view;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatSystemTest {

    private ChatSystem chatSystem;

    @Before
    public void setUp() {
        // Initialize the ChatSystem with your TranslationService implementation
        TranslationService translationService = new TranslationServiceImpl();
        chatSystem = new ChatSystem(translationService);
    }

    @Test
    public void testSendMessageWithoutTranslation() {
        String message = "Hello, how are you?";
        String receivedMessage = chatSystem.sendMessage("User1", "User2", message);
        assertEquals(message, receivedMessage);
    }

    @Test
    public void testSendMessageWithTranslation() {
        String originalMessage = "Hello, how are you?";
        String translatedMessage = "Bonjour, comment ça va?";

        // Mock the TranslationService to return the translated message
        TranslationService translationService = new MockTranslationService(translatedMessage);
        ChatSystem chatSystem = new ChatSystem(translationService);

        String receivedMessage = chatSystem.sendMessage("User1", "User2", originalMessage);
        assertEquals(translatedMessage, receivedMessage);
    }

    @Test
    public void testReceiveMessage() {
        String message = "Hello, how are you?";
        String receivedMessage = chatSystem.receiveMessage("User2", "User1");
        assertEquals(message, receivedMessage);
    }
}
