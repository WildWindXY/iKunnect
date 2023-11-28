package client.use_case.AddFriend;

import client.use_case.SendMessage.SendMessageDataAccessInterface;

public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendDataAccessInterface dataAccess;
    private final AddFriendOutputBoundary addFriendPresenter;

    public AddFriendInteractor(AddFriendDataAccessInterface dataAccess, AddFriendOutputBoundary addFriendPresenter) {
        this.dataAccess = dataAccess;
        this.addFriendPresenter = addFriendPresenter;
    }

    @Override
    public void execute(String username) {
        dataAccess.addFriend(username);
        addFriendPresenter.prepareSuccessView();
    }
}
