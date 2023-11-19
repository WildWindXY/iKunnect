package server.use_case.signup;

import common.packet.Packet;
import common.packet.PacketClientSignup;
import server.entity.PacketIn;
import server.entity.ServerUser;

public interface ServerSignupDataAccessInterface {
    PacketIn<PacketClientSignup> getPacketClientSignup() throws InterruptedException;

    boolean usernameExists(String name);

    void sendTo(Packet packet, int id);

    ServerUser addUser(String username, String password);
}
