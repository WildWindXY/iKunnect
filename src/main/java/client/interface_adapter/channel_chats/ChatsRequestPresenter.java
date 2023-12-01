package client.interface_adapter.channel_chats;

import client.interface_adapter.main.MainViewModel;
import client.use_case.channel_chats.ChatsRequestOutputBoundary;
import client.use_case.channel_chats.ChatsRequestOutputData;
import utils.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatsRequestPresenter implements ChatsRequestOutputBoundary {
    private MainViewModel mainViewModel;

    public ChatsRequestPresenter(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @Override
    public void present(ChatsRequestOutputData output) {
        mainViewModel.setChannelMessages(output.getChats());
    }
}
