package common.packet;

import utils.Tuple;

import java.util.HashMap;

/**
 * The PacketServerGetFriendListResponse class represents a packet containing the response to a request for a user's friend list.
 */
@SuppressWarnings("record")
public class PacketServerGetFriendListResponse implements Packet {
    private final HashMap<Integer, Tuple<String, Integer>> friends;
    private final Status status;

    /**
     * Constructs a PacketServerGetFriendListResponse object with the specified friend list and status.
     *
     * @param friends The HashMap containing the user's friends (Key: friend's user ID, Value: friend's username).
     * @param status  The status of the response (SUCCESS, NOT_LOGGED_IN, SERVER_ERROR).
     */
    public PacketServerGetFriendListResponse(HashMap<Integer, Tuple<String, Integer>> friends, Status status) {
        this.friends = friends;
        this.status = status;
    }

    /**
     * Gets the user's friends.
     *
     * @return The HashMap containing the user's friends (Key: friend's user ID, Value: friend's username). Null if status is not SUCCESS.
     */
    public HashMap<Integer, Tuple<String, Integer>> getFriends() {
        return friends;
    }

    /**
     * Gets the status of the response.
     *
     * @return The status of the response (SUCCESS, NOT_LOGGED_IN, SERVER_ERROR).
     */
    public Status getStatus() {
        return status;
    }

    /**
     * The Status enum represents the possible statuses of the response.
     */
    public enum Status {
        SUCCESS, NOT_LOGGED_IN, SERVER_ERROR
    }
}
