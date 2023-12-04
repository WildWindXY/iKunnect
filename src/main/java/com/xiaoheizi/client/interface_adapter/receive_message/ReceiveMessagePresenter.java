package com.xiaoheizi.client.interface_adapter.receive_message;

// TODO Complete me

import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.interface_adapter.send_message.SendMessageState;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputBoundary;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputData;

public class ReceiveMessagePresenter implements ReceiveMessageOutputBoundary {

    //take the panel from main view
    private final MainViewModel mainViewModel;

    public ReceiveMessagePresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void presentSendMessageResult(ReceiveMessageOutputData outputData) {
        SendMessageState state = mainViewModel.getSendMessageState();
        state.setMessage(outputData.getTimestamp(), outputData.getSenderID(), outputData.getMessage());
        mainViewModel.setSendMessageState(state);
        mainViewModel.fireSendMessagePropertyChanged();
        System.out.println(outputData.getTimestamp() + " " + outputData.getMessage());
    }
}
