import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.data_access.receive_message.ReceiveMessageDataAccess;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageDataAccessInterface;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageInteractor;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputBoundary;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputData;
import com.xiaoheizi.packet.PacketServerTextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReceiveMessageInteractorTest {
    private ReceiveMessageDataAccessInterface dataAccess;
    private ReceiveMessageOutputBoundary outputBoundary;
    private ReceiveMessageInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = mock(ReceiveMessageDataAccessInterface.class);
        outputBoundary = mock(ReceiveMessageOutputBoundary.class);
        interactor = new ReceiveMessageInteractor(dataAccess, outputBoundary);
    }

    @Test
    void testExecuteRetrievesAndPresentsMessages() {
        ReceiveMessageOutputData outputData = new ReceiveMessageOutputData(123, "test", 1561);

        when(dataAccess.receiveMessage()).thenReturn(outputData);

        interactor.execute();

        verify(dataAccess).receiveMessage();
        verify(outputBoundary).presentSendMessageResult(outputData);
    }

}

class ReceiveMessageOutputDataTest {

    @Test
    void testConstructorAndGetters() {
        int senderID = 123;
        String message = "Hello, World!";
        long timestamp = System.currentTimeMillis();

        ReceiveMessageOutputData outputData = new ReceiveMessageOutputData(senderID, message, timestamp);

        assertEquals(senderID, outputData.getSenderID());
        assertEquals(message, outputData.getMessage());
        assertEquals(timestamp, outputData.getTimestamp());
    }
}

class ReceiveMessageDataAccessTest {

    private ServerDataAccessObject serverDataAccessObject;
    private ReceiveMessageDataAccess receiveMessageDataAccess;

    @BeforeEach
    void setUp() {
        serverDataAccessObject = mock(ServerDataAccessObject.class);
        receiveMessageDataAccess = new ReceiveMessageDataAccess(serverDataAccessObject);
    }

    @Test
    void testReceiveMessage() {
        int senderID = 123; // Example sender ID
        String encryptedMessage = "EncryptedMessage"; // Example encrypted message
        long timestamp = System.currentTimeMillis(); // Example timestamp

        // Mock the response from the com.ikun.server
        PacketServerTextMessage mockResponse = new PacketServerTextMessage(senderID, encryptedMessage, timestamp);
        when(serverDataAccessObject.getReceiveMessage()).thenReturn(mockResponse);

        // Execute the method under test
        ReceiveMessageOutputData outputData = receiveMessageDataAccess.receiveMessage();

        // Assertions
        assertEquals(senderID, outputData.getSenderID());
        assertEquals(encryptedMessage, outputData.getMessage()); // Replace with decrypted message if using AES_decrypt
        assertEquals(timestamp, outputData.getTimestamp());
    }
}