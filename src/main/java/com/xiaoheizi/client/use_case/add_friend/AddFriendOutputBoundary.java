package com.xiaoheizi.client.use_case.add_friend;

import com.xiaoheizi.packet.PacketServerGetFriendListResponse;

public interface AddFriendOutputBoundary {
    void prepareSuccessView();

    void friendListSuccessView(PacketServerGetFriendListResponse packetClientGetFriendList);
}
