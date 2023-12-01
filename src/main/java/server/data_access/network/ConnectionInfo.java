package server.data_access.network;

import server.entity.ServerUser;

/**
 * The ConnectionInfo class represents information about a connection, including the connection ID,
 * the associated user, and the status of the connection (UNKNOWN, GUEST, LOGGED).
 */
public class ConnectionInfo {
    private final int connectionId;
    private ServerUser user;

    private Status status = Status.GUEST; //TODO: change to unknown when first handshake implemented to avoid network attack

    /**
     * Constructs a ConnectionInfo object with the specified connection ID.
     *
     * @param connectionId The ID of the connection.
     */
    ConnectionInfo(int connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * Gets the connection ID.
     *
     * @return The connection ID.
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Gets the associated user.
     *
     * @return The associated ServerUser.
     */
    public ServerUser getUser() {
        return user;
    }

    /**
     * Gets the status of the connection.
     *
     * @return The status of the connection (UNKNOWN, GUEST, LOGGED).
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the user and updates the status to LOGGED when a user logs in.
     *
     * @param user The ServerUser associated with the connection.
     */
    public void login(ServerUser user) {
        this.user = user;
        this.status = Status.LOGGED;
    }

    @Override
    public String toString() {
        if (status == Status.LOGGED) {
            return "[ConnectionInfo] connectionId: " + connectionId + ", user: " + user.getUsername();
        } else {
            return "[ConnectionInfo] connectionId: " + connectionId + " not logged in yet.";
        }
    }

    /**
     * The Status enum represents the possible statuses of a connection.
     */
    public enum Status {
        UNKNOWN, GUEST, LOGGED
    }
}
