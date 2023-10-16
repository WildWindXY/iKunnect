package server.use_case.network;

import common.packet.Packet;

interface IConnectionPool {
    void sendAll(Packet packet);
}
