package client.interface_adapter.sendMessage;

// TODO Complete me

import client.interface_adapter.main.MainViewModel;
import client.use_case.sendMessage.SendMessageOutputBoundary;
import client.use_case.sendMessage.SendMessageOutputData;

public class SendMessagePresenter implements SendMessageOutputBoundary {

    //take the panel from main view
    private final MainViewModel mainViewModel;

    public SendMessagePresenter(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void presentSendMessageResult(SendMessageOutputData outputData, String message) {
        SendMessageState state = mainViewModel.getSendMessageState();
        state.setMessage(message);
        state.setTimestamp(outputData.getTimestamp());
        state.setSuccess(outputData.getSuccess());
        state.setSender("");
        mainViewModel.setSendMessageState(state);
        mainViewModel.fireSendMessagePropertyChanged();
        System.out.println(outputData.getTimestamp()+" "+outputData.getSuccess());
    }
}
