package server.data_access.network;

import server.entity.ServerUser;

public class ConnectionInfo {
    private final int connectionId;
    private ServerUser user;

    private Status status = Status.GUEST; //TODO: change to unknown when first handshake implemented to avoid network attack

    ConnectionInfo(int connectionId) {
        this.connectionId = connectionId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public ServerUser getUser() {
        return user;
    }

    public Status getStatus() {
        return status;
    }

    public void login(ServerUser user) {
        this.user = user;
        this.status = Status.LOGGED;
    }

    public enum Status {
        UNKNOWN, GUEST, LOGGED

    }
}
