package server.use_case.friend_request;

import common.packet.Packet;
import common.packet.PacketClientFriendRequest;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerChat;
import server.entity.ServerUser;

/**
 * Interface defining the data access operations related to friend requests.
 */
public interface ServerFriendRequestDataAccessInterface {
    /**
     * Retrieves a packet containing client friend requests from the queue.
     *
     * @return PacketIn object containing client friend requests.
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
