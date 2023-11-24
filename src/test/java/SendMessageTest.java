import client.data_access.ServerDataAccessObject;
import client.data_access.send_message.SendMessageDataAccess;
import client.interface_adapter.SendMessage.SendMessagePresenter;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputData;
import client.use_case.SendMessage.SendMessageInteractor;

import static utils.MessageEncryptionUtils.initKey;

public class SendMessageTest {

    @org.junit.Test
    public void testSendMessage() {
//        try {
//            initKey("1111222233334444");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        SendMessagePresenter sendMessagePresenter = new SendMessagePresenter();
//        ServerDataAccessObject serverDAO = new ServerDataAccessObject("localhost", 8964);
//        SendMessageDataAccessInterface sendMessageDataAccess = new SendMessageDataAccess(serverDAO);
//        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccess,sendMessagePresenter);
//
//        SendMessageInputData data = new SendMessageInputData("test", "me", "you");
//        sendMessageInteractor.execute(data);
    }
}
