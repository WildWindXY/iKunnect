package com.xiaoheizi.client.interface_adapter.add_friend;

import com.xiaoheizi.client.interface_adapter.main.MainViewModel;
import com.xiaoheizi.client.use_case.add_friend.AddFriendOutputBoundary;
import com.xiaoheizi.packet.PacketServerGetFriendListResponse;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final MainViewModel viewModel;

    public AddFriendPresenter(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.fireAddFriendPropertyChanged();
    }

    @Override
    public void friendListSuccessView(PacketServerGetFriendListResponse packetClientGetFriendList) {
        viewModel.setFriendList(packetClientGetFriendList.getFriends());
        viewModel.setChats(packetClientGetFriendList.getChats());
    }
}
