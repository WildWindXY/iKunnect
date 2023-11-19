package server.use_case.get_friend_list;

import common.packet.Packet;
import common.packet.PacketClientGetFriendList;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;


public interface ServerGetFriendListDataAccessInterface {

    PacketIn<PacketClientGetFriendList> getPacketClientGetFriendList() throws InterruptedException;

    void sendTo(Packet packet, ConnectionInfo info);
}
