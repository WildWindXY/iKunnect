package client.use_case.Signup;


import common.packet.PacketServerSignupResponse;

public interface SignupDataAccessInterface {
    PacketServerSignupResponse signup(String username, String password);
}
