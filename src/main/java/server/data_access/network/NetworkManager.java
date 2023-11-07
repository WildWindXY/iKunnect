package server.data_access.network;

import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketDebug;
import common.packet.PacketServerLoginResponse;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkManager {
    private final ConnectionPool connectionPool;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    public NetworkManager() throws IOException {//TODO: Catch it
        this.connectionPool = new ConnectionPool(this, 8964);
        this.connectionPool.start();
    }

    public void packetHandler(Packet packet) {//TODO: This is temporary
        if (packet instanceof PacketDebug) {
            connectionPool.sendAll(packet);
        } else if (packet instanceof PacketClientLogin) {
            terminalMessage.add(((PacketClientLogin) packet).getUsername());
            Packet response = new PacketServerLoginResponse(666000111, true);
            connectionPool.sendAll(response);
        }
    }

    public void addMessageToTerminal(){

    }

    public String getMessageToTerminal() {
        try {
            return terminalMessage.take();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }
}
