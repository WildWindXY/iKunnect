package server.use_case.login;

import common.packet.Packet;
import common.packet.PacketClientLogin;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerUser;
import server.entity.ServerUsers;
import utils.Tuple;

//TODO: doc
public interface ServerLoginDataAccessInterface {
    PacketIn<PacketClientLogin> getPacketClientLogin() throws InterruptedException;

    void sendTo(Packet packet, ConnectionInfo info);

    Tuple<Integer, ServerUser> checkPassword(String username, String password);
}
