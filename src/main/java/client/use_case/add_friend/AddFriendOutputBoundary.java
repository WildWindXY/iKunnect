package client.use_case.add_friend;

import common.packet.PacketClientGetFriendList;
import common.packet.PacketServerGetFriendListResponse;

public interface AddFriendOutputBoundary {
    void prepareSuccessView();

    void friendListSuccessView(PacketServerGetFriendListResponse packetClientGetFriendList);
}
