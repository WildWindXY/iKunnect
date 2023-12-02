package common.packet;

/**
 * The PacketServerSignupResponse class represents a server response packet for client signup requests.
 * It includes information about the user ID and the status of the signup operation.
 */
@SuppressWarnings("record")
public class PacketServerSignupResponse implements Packet {

    private final int userID;
    private final Status status;

    /**
     * Constructs a PacketServerSignupResponse with the specified user ID and status.
     *
     * @param userID The user ID associated with the signup response.
     * @param status The status of the signup operation.
     */
    public PacketServerSignupResponse(int userID, Status status) {
        this.userID = userID;
        this.status = status;
    }

    /**
     * Gets the user ID associated with the signup response, should not be used when status is not SUCCESS
     *
     * @return The user ID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the status of the signup operation.
     *
     * @return The signup status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns a string representation of the PacketServerSignupResponse.
     *
     * @return A string containing the user ID and status.
     */
    @Override
    public String toString() {
        return "[PacketServerSignupResponse] userID: " + userID + ", status: " + status;
    }

    /**
     * Enum representing possible statuses for a signup response.
     */
    public enum Status {
        SUCCESS, ALREADY_LOGGED_IN, USERNAME_EXISTS, TOO_SHORT, TOO_LONG, INVALID_CHARACTERS, NULL_ATTRIBUTE
    }
}
