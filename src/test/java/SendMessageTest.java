import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import client.data_access.ServerDataAccessObject;
import client.data_access.send_message.SendMessageDataAccess;
import client.use_case.send_message.*;
import common.packet.PacketClientTextMessage;
import common.packet.PacketServerTextMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SendMessageInteractorTest {
    private SendMessageDataAccessInterface dataAccess;
    private SendMessageOutputBoundary sendMessagePresenter;
    private SendMessageInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = mock(SendMessageDataAccessInterface.class);
        sendMessagePresenter = mock(SendMessageOutputBoundary.class);
        interactor = new SendMessageInteractor(dataAccess, sendMessagePresenter);
    }

    @Test
    void testExecuteSendsMessageAndPresentsResult() {
        SendMessageInputData inputData = new SendMessageInputData( "Hello, world!", "test", 123456 );
        SendMessageOutputData outputData = new SendMessageOutputData(true, 121212, "Message sent");

        when(dataAccess.sendMessage(inputData)).thenReturn(outputData);

        interactor.execute(inputData);

        verify(dataAccess).sendMessage(inputData);
        verify(sendMessagePresenter).presentSendMessageResult(outputData, inputData.getMessage());
    }
}

class SendMessageOutputDataTest {

    @Test
    void testConstructorAndGetters() {
        boolean success = true;
        long timestamp = System.currentTimeMillis();
        String message = "Test Message";

        SendMessageOutputData outputData = new SendMessageOutputData(success, timestamp, message);

        assertEquals(success, outputData.getSuccess());
        assertEquals(timestamp, outputData.getTimestamp());
        assertEquals(message, outputData.getMessage());
    }
}

class SendMessageInputDataTest {

    @Test
    void testConstructorAndGetters() {
        String message = "Hello!";
        String senderID = "sender123";
        int recipientID = 456;

        SendMessageInputData inputData = new SendMessageInputData(message, senderID, recipientID);

        assertEquals(message, inputData.getMessage());
        assertEquals(senderID, inputData.getSender());
        assertEquals(recipientID, inputData.getRecipientID());
    }
}

class SendMessageDataAccessTest {

    private ServerDataAccessObject serverDataAccessObject;
    private SendMessageDataAccess sendMessageDataAccess;

    @BeforeEach
    void setUp() {
        serverDataAccessObject = mock(ServerDataAccessObject.class);
        sendMessageDataAccess = new SendMessageDataAccess(serverDataAccessObject);
    }

    @Test
    void testSendMessage() {
        String sender = "sender"; // Example sender ID
        int recipientID = 123; // Example recipient ID
        String message = "Hello, World!";

        // Create input data
        SendMessageInputData inputData = new SendMessageInputData(message, sender, recipientID);

        // Mock the response from the server
        PacketServerTextMessageResponse mockResponse = new PacketServerTextMessageResponse(1, 123123, PacketServerTextMessageResponse.Status.RECEIVED);
        when(serverDataAccessObject.getSendMessageResponse()).thenReturn(mockResponse);

        // Execute the method under test
        SendMessageOutputData outputData = sendMessageDataAccess.sendMessage(inputData);

        // Verify interactions and assertions
        verify(serverDataAccessObject).sendPacket(any(PacketClientTextMessage.class));
        assertTrue(outputData.getSuccess());
        assertNotNull(outputData.getTimestamp());
        assertEquals(message, outputData.getMessage());
    }
}