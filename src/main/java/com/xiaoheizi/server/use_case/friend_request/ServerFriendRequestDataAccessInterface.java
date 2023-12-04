package com.xiaoheizi.server.use_case.friend_request;

import com.xiaoheizi.packet.Packet;
import com.xiaoheizi.packet.PacketClientFriendRequest;
import com.xiaoheizi.server.data_access.network.ConnectionInfo;
import com.xiaoheizi.server.entity.PacketIn;
import com.xiaoheizi.server.entity.ServerChat;
import com.xiaoheizi.server.entity.ServerUser;

/**
 * Interface defining the data access operations related to friend requests.
 */
public interface ServerFriendRequestDataAccessInterface {
    /**
     * Retrieves a packet containing com.ikun.client friend requests from the queue.
     *
     * @return PacketIn object containing com.ikun.client friend requests.
     * @throws InterruptedException if the operation is interrupted.
     */
    PacketIn<PacketClientFriendRequest> getPacketClientFriendRequests() throws InterruptedException;

    /**
     * Retrieves a user based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The ServerUser object corresponding to the username.
     */
    ServerUser getUserByUsername(String username);

    ServerChat createChat();

    /**
     * Sends a packet to the specified connection information.
     *
     * @param packet The packet to send.
     * @param info   The connection information to which the packet should be sent.
     */
    void sendTo(Packet packet, ConnectionInfo info);

    ConnectionInfo getConnectionInfo(int id);
}
