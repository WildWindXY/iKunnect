package client.interface_adapter.add_friend;

import client.interface_adapter.main.MainViewModel;
import client.use_case.add_friend.AddFriendOutputBoundary;

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
