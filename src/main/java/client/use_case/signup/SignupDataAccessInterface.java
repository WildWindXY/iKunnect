package client.use_case.signup;


import common.packet.PacketServerSignupResponse;

public interface SignupDataAccessInterface {
    PacketServerSignupResponse signup(String username, String password);
}
