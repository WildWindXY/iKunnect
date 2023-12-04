package com.xiaoheizi.client.use_case.signup;


import com.xiaoheizi.packet.PacketServerSignupResponse;

public interface SignupDataAccessInterface {
    PacketServerSignupResponse signup(String username, String password);
}
