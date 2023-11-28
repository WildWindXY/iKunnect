package client.data_access.add_friend;

import client.data_access.ServerDataAccessObject;
import client.use_case.add_friend.AddFriendDataAccessInterface;

public class AddFriendDataAccess implements AddFriendDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;
    public AddFriendDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }


    @Override
    public void addFriend(String username) {
        serverDataAccessObject.addFriend(username);
    }
}
