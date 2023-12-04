package com.xiaoheizi.server.entity;

import com.xiaoheizi.packet.Packet;
import com.xiaoheizi.server.data_access.network.ConnectionInfo;

/**
 * The PacketIn class represents an incoming packet along with the associated connection information.
 *
 * @param <T> The type of the packet.
 */
@SuppressWarnings("record")
public class PacketIn<T extends Packet> {
    private final ConnectionInfo info;
    private final T packet;

    /**
     * Constructs a PacketIn object with the specified connection information and packet.
     *
     * @param info   The ConnectionInfo associated with the packet.
     * @param packet The incoming packet.
     */
    public PacketIn(ConnectionInfo info, T packet) {
        this.info = info;
        this.packet = packet;
    }

    /**
     * Gets the connection information associated with the packet.
     *
     * @return The ConnectionInfo object.
     */
    public ConnectionInfo getConnectionInfo() {
        return info;
    }

    /**
     * Gets the incoming packet.
     *
     * @return The incoming packet.
     */
    public T getPacket() {
        return packet;
    }
}
