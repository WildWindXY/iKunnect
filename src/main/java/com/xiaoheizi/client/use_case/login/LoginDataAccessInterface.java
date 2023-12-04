package com.xiaoheizi.client.use_case.login;

import com.xiaoheizi.packet.PacketServerGetFriendListResponse;
import com.xiaoheizi.packet.PacketServerLoginResponse;

public interface LoginDataAccessInterface {
    PacketServerLoginResponse login(String username, String password);

    PacketServerGetFriendListResponse getFriendList();
}
