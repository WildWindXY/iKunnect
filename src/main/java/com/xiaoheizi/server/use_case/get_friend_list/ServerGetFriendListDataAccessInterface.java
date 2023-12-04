package com.xiaoheizi.server.use_case.get_friend_list;

import com.xiaoheizi.packet.Packet;
import com.xiaoheizi.packet.PacketClientGetFriendList;
import com.xiaoheizi.server.data_access.network.ConnectionInfo;
import com.xiaoheizi.server.entity.PacketIn;
import com.xiaoheizi.server.entity.ServerUser;
import com.xiaoheizi.utils.Triple;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * The ServerGetFriendListDataAccessInterface interface defines the methods for accessing data related to getting a user's friend list.
 */
public interface ServerGetFriendListDataAccessInterface {
    /**
     * Gets the incoming packet for getting a friend list, blocking if no packet is available.
     *
     * @return The PacketIn object containing the request packet and connection information.
     * @throws InterruptedException If the thread is interrupted while waiting for a packet.
     */
    PacketIn<PacketClientGetFriendList> getPacketClientGetFriendList() throws InterruptedException;

    /**
     * Sends a packet to the specified connection using the ConnectionInfo.
     * If the connection with the given ConnectionInfo is found, the packet is added to the connection's send queue.
     * If the connection is not found, a message is added to the network manager's terminal indicating the unsent packet.
     *
     * @param packet The packet to be sent.
     * @param info   The ConnectionInfo of the target connection.
     */
    void sendTo(Packet packet, ConnectionInfo info);

    ServerUser getUserById(int id);

    HashMap<Integer, List<Triple<Long, Integer, String>>> getChats(Collection<Integer> friendIds);
}
