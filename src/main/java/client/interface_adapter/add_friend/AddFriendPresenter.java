package client.interface_adapter.add_friend;

import client.interface_adapter.main.MainViewModel;
import client.use_case.add_friend.AddFriendOutputBoundary;
import common.packet.PacketClientGetFriendList;
import common.packet.PacketServerGetFriendListResponse;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final MainViewModel viewModel;

    public AddFriendPresenter(MainViewModel viewModel){
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
