package server.data_access.network;

import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketDebug;
import common.packet.PacketServerLoginResponse;
import server.data_access.DataAccess;


import java.io.IOException;

import static utils.MessageEncryptionUtils.AES_decrypt;

public class NetworkManager {
    private final ConnectionPool connectionPool;
    private final DataAccess dataAccess;


    public NetworkManager(DataAccess dataAccess) throws IOException {//TODO: Catch it
        this.dataAccess = dataAccess;
        this.connectionPool = new ConnectionPool(this, 8964);
    }

    public void packetHandler(Packet packet) {//TODO: This is temporary
        if (packet instanceof PacketDebug) {
            connectionPool.sendAll(packet);
        } else if (packet instanceof PacketClientLogin) {
            addMessageToTerminal(((PacketClientLogin) packet).getUsername());
            Packet response = new PacketServerLoginResponse(666000111, true);
            connectionPool.sendAll(response);
        } else if(packet instanceof PacketClientMessage){
            System.out.println(((PacketClientMessage) packet));
            try {
                System.out.println("Message After Decryption: " + AES_decrypt(((PacketClientMessage) packet).getEncryptedMessage()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Packet response = new PacketServerSendMessageResponse(System.currentTimeMillis(), true);
            connectionPool.sendAll(response);
        }
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
