package server.data_access.network;

import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketDebug;
import common.packet.PacketServerLoginResponse;
import server.data_access.DataAccess;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkManager {
    private final IConnectionPool connectionPool;
    private final DataAccess dataAccess;


    public NetworkManager(DataAccess dataAccess) throws IOException {//TODO: Catch it
        this.dataAccess = dataAccess;
        this.connectionPool = new ConnectionPool(this, 8964);
        this.connectionPool.start();
    }

    public void packetHandler(Packet packet) {//TODO: This is temporary
        if (packet instanceof PacketDebug) {
            connectionPool.sendAll(packet);
        } else if (packet instanceof PacketClientLogin) {
            addMessageToTerminal(((PacketClientLogin) packet).getUsername());
            Packet response = new PacketServerLoginResponse(666000111, true);
            connectionPool.sendAll(response);
        }
    }

    public void addMessageToTerminal(String message) {
        dataAccess.addTerminalMessage(message);
    }

    public void shutdown(){
        connectionPool.close();
    }
}
