package client.use_case.login;

import common.packet.PacketServerLoginResponse;

public interface LoginDataAccessInterface {
    PacketServerLoginResponse login(String username, String password);
}
