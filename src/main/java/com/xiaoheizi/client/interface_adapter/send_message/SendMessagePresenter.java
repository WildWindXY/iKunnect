package com.xiaoheizi.client.interface_adapter.send_message;

import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.use_case.send_message.SendMessageOutputBoundary;
import com.xiaoheizi.client.use_case.send_message.SendMessageOutputData;

public class SendMessagePresenter implements SendMessageOutputBoundary {

    //take the panel from main view
    private final MainViewModel mainViewModel;

    public SendMessagePresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void presentSendMessageResult(SendMessageOutputData outputData, String message) {
        SendMessageState state = mainViewModel.getSendMessageState();
        state.setMessage(outputData.getTimestamp(), mainViewModel.getMyUserId(), outputData.getMessage());
        mainViewModel.setSendMessageState(state);
        mainViewModel.fireSendMessagePropertyChanged();
        System.out.println(outputData.getSuccess() + " " + outputData.getSuccess());
    }
}
