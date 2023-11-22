package client.use_case.Login;

import common.packet.PacketServerLoginResponse;

public interface LoginDataAccessInterface {
    PacketServerLoginResponse login(String username, String password);
}
