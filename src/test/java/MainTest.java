import com.xiaoheizi.client.app.MainUseCaseFactory;
import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.use_case.add_friend.AddFriendDataAccessInterface;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastDataAccessInterface;
import com.xiaoheizi.client.use_case.high_contrast.HighContrastOutputData;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageDataAccessInterface;
import com.xiaoheizi.client.use_case.send_message.SendMessageDataAccessInterface;
import com.xiaoheizi.client.use_case.translate.TranslateDataAccessInterface;
import com.xiaoheizi.client.view.MainView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        when(highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST)).thenReturn(new HighContrastOutputData(true));

        MainView result = MainUseCaseFactory.create(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, mainViewModel);

        assertNotNull(result);
        // More assertions can be added here to verify the setup of MainView or MainController
        // But that might require accessing the internals of these objects, or refactoring for better testability
    }
}
