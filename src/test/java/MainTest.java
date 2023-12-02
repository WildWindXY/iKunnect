import client.app.MainUseCaseFactory;
import client.interface_adapter.main.MainViewModel;
import client.use_case.add_friend.AddFriendDataAccessInterface;
import client.use_case.high_contrast.HighContrastDataAccessInterface;
import client.use_case.high_contrast.HighContrastOutputData;
import client.use_case.receive_message.ReceiveMessageDataAccessInterface;
import client.use_case.send_message.SendMessageDataAccessInterface;
import client.use_case.translate.TranslateDataAccessInterface;
import client.view.MainView;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MainUseCaseFactoryTest {

    @Test
    void testCreate() {
        String myUsername = "testUser";
        SendMessageDataAccessInterface sendMessageDataAccessObject = mock(SendMessageDataAccessInterface.class);
        ReceiveMessageDataAccessInterface receiveMessageDataAccessObject = mock(ReceiveMessageDataAccessInterface.class);
        TranslateDataAccessInterface translateDataAccessObject = mock(TranslateDataAccessInterface.class);
        HighContrastDataAccessInterface highContrastDataAccessObject = mock(HighContrastDataAccessInterface.class);
        AddFriendDataAccessInterface addFriendDataAccessObject = mock(AddFriendDataAccessInterface.class);
        MainViewModel mainViewModel = mock(MainViewModel.class);

        // Configure the mock for highContrastDataAccessObject
        when(highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST)).thenReturn(new HighContrastOutputData(1));

        MainView result = MainUseCaseFactory.create(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, mainViewModel);

        assertNotNull(result);
        // More assertions can be added here to verify the setup of MainView or MainController
        // But that might require accessing the internals of these objects, or refactoring for better testability
    }
}
