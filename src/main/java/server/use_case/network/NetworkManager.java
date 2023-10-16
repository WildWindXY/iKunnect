package server.use_case.network;

import common.packet.Packet;
import common.packet.PacketDebug;

import java.io.IOException;

public class NetworkManager {
    private final ConnectionPool connectionPool;

    public NetworkManager() throws IOException {//TODO: Catch it
        this.connectionPool = new ConnectionPool(this, 8964);
        this.connectionPool.start();
    }

    public void packetHandler(Packet packet) {//TODO: This is temporary
        if (packet instanceof PacketDebug) {
            System.out.println(packet);
            connectionPool.sendAll(packet);
        }
    }
}
