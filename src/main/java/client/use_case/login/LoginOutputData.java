package client.use_case.login;

import utils.Triple;
import utils.Tuple;

import java.util.HashMap;
import java.util.List;

public class LoginOutputData {

    private final String username;

    private final int userId;
    private final HashMap<Integer, Tuple<String, Integer>> friends;
    private final HashMap<Integer, List<Triple<Long, Integer, String>>> chats;

    public LoginOutputData(String username, HashMap<Integer, Tuple<String, Integer>> friends, HashMap<Integer, List<Triple<Long, Integer, String>>> chats, int userId) {
        this.username = username;
        this.friends = friends;
        this.chats = chats;
        this.userId = userId;
    }

    public HashMap<Integer, Tuple<String, Integer>> getFriends() {
        return friends;
    }

    public HashMap<Integer, List<Triple<Long, Integer, String>>> getChats() {
        return chats;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
