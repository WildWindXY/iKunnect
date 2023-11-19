package server.entity;

import common.packet.Packet;

@SuppressWarnings("record")
public class PacketIn<T extends Packet> {
    private final int connectionId;
    private final T packet;

    public PacketIn(int connectionId, T packet) {
        this.connectionId = connectionId;
        this.packet = packet;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public T getPacket() {
        return packet;
    }
}
