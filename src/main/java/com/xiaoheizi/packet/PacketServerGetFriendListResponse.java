package com.xiaoheizi.packet;

import com.xiaoheizi.utils.Triple;
import com.xiaoheizi.utils.Tuple;

import java.util.HashMap;
import java.util.List;

/**
 * The PacketServerGetFriendListResponse class represents a packet containing the response to a request for a user's friend list.
 */
@SuppressWarnings("record")
public class PacketServerGetFriendListResponse implements Packet {
    private final HashMap<Integer, Tuple<String, Integer>> friends;
    private final HashMap<Integer, List<Triple<Long, Integer, String>>> chats;
    private final Status status;

    /**
     * Constructs a PacketServerGetFriendListResponse object with the specified friend list and status.
     *
     * @param friends The HashMap containing the user's friends (Key: friend's user ID, Value: friend's username).
     * @param status  The status of the response (SUCCESS, NOT_LOGGED_IN, SERVER_ERROR).
     */
    public PacketServerGetFriendListResponse(HashMap<Integer, Tuple<String, Integer>> friends, HashMap<Integer, List<Triple<Long, Integer, String>>> chats, Status status) {
        this.friends = friends;
        this.chats = chats;
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

    public HashMap<Integer, List<Triple<Long, Integer, String>>> getChats() {
        return chats;
    }

    /**
     * Gets the status of the response.
     *
     * @return The status of the response (SUCCESS, NOT_LOGGED_IN, SERVER_ERROR).
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "[PacketServerGetFriendListResponse] status: " + status + ", friends: " + friends + ", chats: " + chats;
    }

    /**
     * The Status enum represents the possible statuses of the response.
     */
    public enum Status {
        SUCCESS, NOT_LOGGED_IN, SERVER_ERROR
    }
}
