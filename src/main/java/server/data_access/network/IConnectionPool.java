package server.data_access.network;

import common.packet.Packet;

interface IConnectionPool {
    void sendAll(Packet packet);
}
