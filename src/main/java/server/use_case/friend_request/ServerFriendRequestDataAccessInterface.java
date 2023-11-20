package server.use_case.friend_request;

import common.packet.Packet;
import common.packet.PacketClientFriendRequest;
import common.packet.PacketClientGetFriendList;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerUser;

public interface ServerFriendRequestDataAccessInterface {

    PacketIn<PacketClientFriendRequest> getPacketClientFriendRequests() throws InterruptedException;

    ServerUser getUserByUsername(String username);

    void sendTo(Packet packet, ConnectionInfo info);
}
