package client.interface_adapter.AddFriend;

import client.interface_adapter.Main.MainViewModel;
import client.use_case.AddFriend.AddFriendOutputBoundary;

public class AddFriendPresenter implements AddFriendOutputBoundary {

    private final MainViewModel viewModel;

    public AddFriendPresenter(MainViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.fireAddFriendPropertyChanged();
    }
}
