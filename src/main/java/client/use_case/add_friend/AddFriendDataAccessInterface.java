package client.use_case.add_friend;

import common.packet.PacketServerGetFriendListResponse;

public interface AddFriendDataAccessInterface {
    void addFriend(String username);

    PacketServerGetFriendListResponse getFriendList();
}
