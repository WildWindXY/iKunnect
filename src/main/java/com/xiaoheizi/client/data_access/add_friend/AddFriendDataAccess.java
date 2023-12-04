package com.xiaoheizi.client.data_access.add_friend;

import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.use_case.add_friend.AddFriendDataAccessInterface;
import com.xiaoheizi.packet.PacketServerFriendRequestFrom;
import com.xiaoheizi.packet.PacketServerGetFriendListResponse;

public class AddFriendDataAccess implements AddFriendDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public AddFriendDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }


    @Override
    public void addFriend(String username) {
        serverDataAccessObject.addFriend(username);
    }

    @Override
    public PacketServerGetFriendListResponse getFriendList() {
        return serverDataAccessObject.getFriendList();
    }

    @Override
    public PacketServerFriendRequestFrom getFriendRequest() {
        return serverDataAccessObject.getFriendRequest();
    }

}
