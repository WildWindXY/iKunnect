package com.xiaoheizi.packet;

@SuppressWarnings("record")

public class PacketServerLoginResponse implements Packet {
    private final int userID;
    private final Status status;

    public PacketServerLoginResponse(int userID, Status status) {
        this.userID = userID;
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "[PacketServerLoginResponse] userID: " + userID + ", status: " + status;
    }

    public enum Status {
        SUCCESS, ALREADY_LOGGED_IN, NO_SUCH_USERNAME, WRONG_PASSWORD, NULL_ATTRIBUTE
    }
}