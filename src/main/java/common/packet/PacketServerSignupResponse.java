package common.packet;

public class PacketServerSignupResponse implements Packet {

    private final int userID;
    private final Status status;

    public PacketServerSignupResponse(int userID, Status status) {
        this.userID = userID;
        this.status = status;
    }

    public long getUserID() {
        return userID;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return userID + " " + status;
    }

    public enum Status {
        SUCCESS, USERNAME_EXISTS, TOO_SHORT, TOO_LONG, INVALID_CHARACTERS, NULL_ATTRIBUTE
    }
}
