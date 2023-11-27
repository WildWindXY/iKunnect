package server.entity;

import com.google.gson.annotations.Expose;
import server.data_access.local.FileManager;
import utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The ServerUsers class represents a collection of users on the server.
 * It implements the IFile interface for file-related operations and manages
 * user data.
 */
public class ServerUsers implements IFile<ServerUsers> {
    /**
     * The default path for storing user data in JSON format.
     */
    public static final String PATH = "user.json";
    private static ServerUsers instance;
    /**
     * The list of users registered on the server.
     */
    @Expose
    private List<User> users = new ArrayList<>();

    /**
     * Constructor that enforce the singleton pattern.
     */
    public ServerUsers() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException("Only one ServerUsers instance should exists");
        }
    }

    /**
     * Gets the default instance of the ServerUsers class.
     *
     * @return The default instance of ServerUsers.
     */
    public static ServerUsers getDefault() {
        return new ServerUsers();
    }

    /**
     * Saves the modifications made to the ServerUsers instance.
     */
    public static void save() {
        FileManager.registerModifiedFile(instance);
    }

    /**
     * Adds a new user to the server with the specified username and password.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The newly added user as a ServerUser.
     */
    public ServerUser addUser(String username, String password) {
        User user = new User(users.size(), username, password);
        users.add(user);
        FileManager.registerModifiedFile(this);
        return user;
    }

    /**
     * Retrieves a server user based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The server user with the specified username, or null if not found.
     */
    public ServerUser getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    /**
     * Retrieves a server user based on the provided user ID.
     *
     * @param userId The user ID of the user to retrieve.
     * @return The server user with the specified user ID.
     */
    public ServerUser getUser(int userId) {
        return users.get(userId);
    }

    /**
     * Checks the password for a given username in the server user database.
     *
     * @param username The username to check for.
     * @param password The password to validate against the stored password.
     * @return A Tuple representing the result of the password check.
     * - If the username and password match, returns Tuple with integer 0 and the corresponding ServerUser.
     * - If the username exists but the password does not match, returns Tuple with integer -1 and null.
     * - If the username does not exist, returns Tuple with integer -2 and null.
     */
    public Tuple<Integer, ServerUser> checkPassword(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.password.equals(password)) {
                    return new Tuple<>(0, user);
                } else {
                    return new Tuple<>(-1, null);
                }
            }
        }
        return new Tuple<>(-2, null);
    }

    /**
     * Gets the file path for storing user data.
     *
     * @return The file path for storing user data.
     */
    @Override
    public String getPath() {
        return PATH;
    }

    /**
     * The Friend class represents a friend relationship with another user.
     */
    private static class Friend {
        @Expose
        private int friendId;
        @Expose
        private int chatId;
        @Expose
        private boolean isFriend = true;

        Friend(int friendId, int chatId) {
            this.friendId = friendId;
            this.chatId = chatId;
        }
    }

    public class User implements ServerUser {
        @Expose
        private int userId;
        @Expose
        private String username;
        @Expose
        private String password;
        @Expose
        private List<Friend> friends = new LinkedList<>();
        @Expose
        private List<Integer> incomingFriendRequests = new LinkedList<>();

        /**
         * The User class represents a server user with associated details.
         */
        private User(int userId, String username, String password) {
            this.userId = userId;
            this.username = username;
            this.password = password;
        }

        /**
         * Checks if the user had a friend with the specified ID.
         *
         * @param friendId The user ID of the potential friend.
         * @return True if the user had a friend with the specified ID; otherwise, false.
         */
        public boolean wasFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Checks if a user is a friend with the specified friendId.
         *
         * @param friendId The ID of the user to check for friendship.
         * @return True if the user is a friend with the specified friendId; otherwise, false.
         */
        public boolean isFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    return friend.isFriend;
                }
            }
            return false;
        }

        /**
         * Makes up a friend relationship with the specified friend ID.
         *
         * @param friendId The user ID of the friend to makeup.
         */
        public void makeupFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    friend.isFriend = true;
                    return;
                }
            }
        }

        /**
         * Adds a friend relationship with the specified friend ID and chat ID.
         *
         * @param friendId The user ID of the friend to add.
         * @param chatId   The chat ID associated with the friend relationship.
         */
        public void addFriend(int friendId, int chatId) {
            friends.add(new Friend(friendId, chatId));
        }

        public int getChatId(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    return friend.chatId;
                }
            }
            return -1;
        }

        /**
         * Removes a friend relationship with the specified friend ID.
         *
         * @param friendId The user ID of the friend to remove.
         */
        public void removeFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    friend.isFriend = false;
                    return;
                }
            }
        }

        /**
         * Adds a friend request from a user with the specified friendId.
         *
         * @param friendId The ID of the user sending the friend request.
         */
        public void addFriendRequest(int friendId) {
            if (!incomingFriendRequests.contains(friendId)) {
                incomingFriendRequests.add(friendId);
            }
        }

        /**
         * Removes a friend request from a user with the specified friendId.
         *
         * @param friendId The ID of the user whose friend request should be removed.
         */
        public void removeFriendRequest(int friendId) {
            incomingFriendRequests.remove((Object) friendId);
        }

        /**
         * Checks if a friend request is received from a user with the specified friendId.
         *
         * @param friendId The ID of the user to check for a friend request.
         * @return True if a friend request is received from the specified user; otherwise, false.
         */
        public boolean isFriendRequestedBy(int friendId) {
            return incomingFriendRequests.contains(friendId);
        }


        /**
         * Gets the user's friend list as a HashMap containing friend IDs and usernames.
         *
         * @return The HashMap representing the user's friend list (Key: friend ID, Value: friend username).
         */
        @Override
        public HashMap<Integer, Tuple<String, Integer>> getFriendList() {
            HashMap<Integer, Tuple<String, Integer>> map = new HashMap<>();
            for (Friend friend : friends) {
                if (friend.isFriend) {
                    map.put(friend.friendId, new Tuple<>(getUser(friend.friendId).getUsername(), getChatId(friend.friendId)));
                }
            }
            return map;
        }

        /**
         * Gets the user ID.
         *
         * @return The user ID.
         */
        @Override
        public int getUserId() {
            return userId;
        }

        /**
         * Gets the username.
         *
         * @return The username.
         */
        @Override
        public String getUsername() {
            return username;
        }

        /**
         * Gets the password.
         *
         * @return The password.
         */
        @Override
        public String getPassword() {
            return password;
        }
    }
}
