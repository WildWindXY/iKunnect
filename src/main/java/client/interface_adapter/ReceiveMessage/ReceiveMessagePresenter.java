package client.interface_adapter.ReceiveMessage;

// TODO Complete me

import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.SendMessage.SendMessageState;
import client.use_case.ReceiveMessage.ReceiveMessageOutputBoundary;
import client.use_case.ReceiveMessage.ReceiveMessageOutputData;
import client.use_case.SendMessage.SendMessageOutputBoundary;
import client.use_case.SendMessage.SendMessageOutputData;

public class ReceiveMessagePresenter implements ReceiveMessageOutputBoundary {

    //take the panel from main view
    private final MainViewModel mainViewModel;

    public ReceiveMessagePresenter(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void presentSendMessageResult(ReceiveMessageOutputData outputData) {
        SendMessageState state = mainViewModel.getState();
        state.setMessage(outputData.getMessage());
        state.setTimestamp(outputData.getTimestamp());
        state.setSuccess(true);
        state.setSender(outputData.getSenderID());
        mainViewModel.setState(state);
        mainViewModel.firePropertyChanged();
        System.out.println(outputData.getTimestamp()+" "+outputData.getMessage());

    }
}
