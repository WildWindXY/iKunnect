package client.interface_adapter.receive_message;

// TODO Complete me

import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.send_message.SendMessageState;
import client.use_case.receive_message.ReceiveMessageOutputBoundary;
import client.use_case.receive_message.ReceiveMessageOutputData;

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
//        state.setSuccess(true);
//        state.setSender(outputData.getSenderID());
        mainViewModel.setSendMessageState(state);
        mainViewModel.fireSendMessagePropertyChanged();
        System.out.println(outputData.getTimestamp()+" "+outputData.getMessage());

    }
}
