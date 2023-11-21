package server.use_case.text_message;

import common.packet.Packet;
import common.packet.PacketClientTextMessage;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerChat;
import server.entity.ServerUser;

/**
 * Interface defining the data access operations related to friend requests.
 */
public interface ServerTextMessageDataAccessInterface {

    public PacketIn<PacketClientTextMessage> getPacketClientTextMessage() throws InterruptedException;

    /**
     * Retrieves a user based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The ServerUser object corresponding to the username.
     */
    ServerUser getUserByUsername(String username);

    ServerUser getUserById(int id);

    ServerChat getChat(int id);

    int addMessage(int senderId, String text);

    /**
     * Sends a packet to the specified connection information.
     *
     * @param packet The packet to send.
     * @param info   The connection information to which the packet should be sent.
     */
    void sendTo(Packet packet, ConnectionInfo info);

    ConnectionInfo getConnectionInfo(int id);
}
