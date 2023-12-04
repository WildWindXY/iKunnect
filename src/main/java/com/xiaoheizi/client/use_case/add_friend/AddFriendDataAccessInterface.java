package com.xiaoheizi.client.use_case.add_friend;

import com.xiaoheizi.packet.PacketServerFriendRequestFrom;
import com.xiaoheizi.packet.PacketServerGetFriendListResponse;

public interface AddFriendDataAccessInterface {
    void addFriend(String username);

    PacketServerGetFriendListResponse getFriendList();

    PacketServerFriendRequestFrom getFriendRequest();
}
