package client.interface_adapter.SendMessage;

// TODO Complete me

import client.use_case.SendMessage.SendMessageOutputBoundary;
import client.use_case.SendMessage.SendMessageOutputData;

public class SendMessagePresenter implements SendMessageOutputBoundary {

    @Override
    public void presentSendMessageResult(SendMessageOutputData outputData) {
        System.out.println(outputData.getTimestamp()+" "+outputData.getSuccess());
    }
}
