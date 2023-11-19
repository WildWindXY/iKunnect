package server.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
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
    /**
     * The list of users registered on the server.
     */
    @Expose
    private List<User> users = new ArrayList<>();

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
     * Gets the file path for storing user data.
     *
     * @return The file path for storing user data.
     */
    public String getPath() {
        return PATH;
    }

    /**
     * Gets the default instance of the ServerUsers class.
     *
     * @return The default instance of ServerUsers.
     */
    public static ServerUsers getDefault() {
        return new ServerUsers();
    }

    public static class User implements ServerUser {
        @Expose
        private int userId;
        @Expose
        private String username;
        @Expose
        private String password;
        @Expose
        private List<Friend> friends = new ArrayList<>();

        /**
         * The User class represents a server user with associated details.
         */
        User(int userId, String username, String password) {
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
         * Gets the user ID.
         *
         * @return The user ID.
         */
        public int getUserId() {
            return userId;
        }

        /**
         * Gets the username.
         *
         * @return The username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Gets the password.
         *
         * @return The password.
         */
        public String getPassword() {
            return password;
        }
    }

    /**
     * The Friend class represents a friend relationship with another user.
     */
    public static class Friend {
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

        /**
         * Gets the friend's user ID.
         *
         * @return The friend's user ID.
         */
        public int getFriendId() {
            return friendId;
        }

        /**
         * Gets the chat ID associated with the friend relationship.
         *
         * @return The chat ID.
         */
        public int getChatId() {
            return chatId;
        }

        /**
         * Checks if this friend was deleted or not.
         *
         * @return True if this user is a friend; otherwise, false.
         */
        public boolean isFriend() {
            return isFriend;
        }
    }
}
