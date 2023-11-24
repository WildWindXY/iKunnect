package client.interface_adapter.receiveMessage;

// TODO Complete me

import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.sendMessage.SendMessageState;
import client.use_case.receiveMessage.ReceiveMessageOutputBoundary;
import client.use_case.receiveMessage.ReceiveMessageOutputData;

public class ReceiveMessagePresenter implements ReceiveMessageOutputBoundary {

    //take the panel from main view
    private final MainViewModel mainViewModel;

    public ReceiveMessagePresenter(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void presentSendMessageResult(ReceiveMessageOutputData outputData) {
        SendMessageState state = mainViewModel.getSendMessageState();
        state.setMessage(outputData.getMessage());
        state.setTimestamp(outputData.getTimestamp());
        state.setSuccess(true);
        state.setSender(outputData.getSenderID());
        mainViewModel.setSendMessageState(state);
        mainViewModel.fireSendMessagePropertyChanged();
        System.out.println(outputData.getTimestamp()+" "+outputData.getMessage());

    }
}
