package server.data_access.network;

import common.packet.*;

import java.io.IOException;

import static utils.MessageEncryptionUtils.AES_decrypt;

public class NetworkManager {
    private final ConnectionPool connectionPool;

    public NetworkManager() throws IOException {//TODO: Catch it
        this.connectionPool = new ConnectionPool(this, 8964);
        this.connectionPool.start();
    }

    public void packetHandler(Packet packet) {//TODO: This is temporary
        if (packet instanceof PacketDebug) {
            connectionPool.sendAll(packet);
        } else if(packet instanceof PacketClientLogin){
            System.out.println(((PacketClientLogin) packet).getUsername());
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
}
