package com.xiaoheizi.server.data_access.network;

import com.xiaoheizi.packet.*;
import com.xiaoheizi.server.data_access.DataAccess;
import com.xiaoheizi.server.entity.PacketIn;

import java.io.IOException;

public class NetworkManager {
    private final ConnectionPool connectionPool;
    private final DataAccess dataAccess;


    public NetworkManager(DataAccess dataAccess) throws IOException {//TODO: Catch it
        this.dataAccess = dataAccess;
        this.connectionPool = new ConnectionPool(this, 0x2304);
    }

    public void packetHandler(Packet packet, ConnectionInfo info) {
        if (packet instanceof PacketDebug) {
            connectionPool.sendAll(packet);
        } else if (packet instanceof PacketClientSignup) {
            dataAccess.addPacketClientSignup(new PacketIn<>(info, (PacketClientSignup) packet));
        } else if (packet instanceof PacketClientLogin) {
            dataAccess.addPacketClientLogin(new PacketIn<>(info, (PacketClientLogin) packet));
        } else if (packet instanceof PacketClientGetFriendList) {
            dataAccess.addPacketClientGetFriendList(new PacketIn<>(info, (PacketClientGetFriendList) packet));
        } else if (packet instanceof PacketClientFriendRequest) {
            dataAccess.addPacketClientFriendRequest(new PacketIn<>(info, (PacketClientFriendRequest) packet));
        } else if (packet instanceof PacketClientTextMessage) {
            dataAccess.addPacketClientTextMessage(new PacketIn<>(info, (PacketClientTextMessage) packet));
//            System.out.println(((PacketClientTextMessage) packet));
//            try {
//                System.out.println("Sender:" + "Send To" + ((PacketClientTextMessage) packet).getRecipient() + "Message After Decryption: " + AES_decrypt(((PacketClientTextMessage) packet).getEncryptedMessage()));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            Packet response = new PacketServerTextMessageResponse(System.currentTimeMillis(), true);
//            connectionPool.sendAll(response);
//            Packet response1 = null;
//            try {
//                response1 = new PacketServerMessage("cxk", AES_encrypt("坤坤天下第一"), System.currentTimeMillis());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            connectionPool.sendAll(response1);
        }
    }

    public ConnectionInfo getConnectionInfo(int id) {
        return connectionPool.getConnectionInfo(id);
    }

    /**
     * Sends a packet to the specified connection using the ConnectionInfo.
     * If the connection with the given ConnectionInfo is found, the packet is added to the connection's send queue.
     * If the connection is not found, a message is added to the network manager's terminal indicating the unsent packet.
     *
     * @param packet The packet to be sent.
     * @param info   The ConnectionInfo of the target connection.
     */
    public void sendTo(Packet packet, ConnectionInfo info) {
        connectionPool.sendTo(packet, info);
    }

    /**
     * Adds a message to the terminal output.
     * <p>
     * This method is used to add a message to the terminal output, which is typically displayed
     * to provide information or status updates.
     *
     * @param message The message to be added to the terminal output.
     */
    void addMessageToTerminal(String message) {
        dataAccess.addTerminalMessage(message);
    }

    /**
     * Shuts down the connection pool when called.
     * <p>
     * This method is used to gracefully shut down the connection pool, typically
     * involving the closing of connections and releasing associated resources.
     */
    public void shutdown() {
        connectionPool.close();
    }
}
