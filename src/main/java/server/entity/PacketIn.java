package server.entity;

import common.packet.Packet;
import server.data_access.network.ConnectionInfo;

@SuppressWarnings("record")
public class PacketIn<T extends Packet> {
    private final ConnectionInfo info;
    private final T packet;

    public PacketIn(ConnectionInfo info, T packet) {
        this.info = info;
        this.packet = packet;
    }

    public ConnectionInfo getConnectionInfo() {
        return info;
    }

    public T getPacket() {
        return packet;
    }
}
