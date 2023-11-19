package server.entity;

import java.util.HashMap;

/**
 * The ServerUser interface defines the common behavior for server user-related operations.
 */
public interface ServerUser {
    /**
     * Checks if a user was friends with another user.
     *
     * @param friendId The ID of the potential friend.
     * @return True if the user was friends with the specified friend, otherwise false.
     */
    boolean wasFriend(int friendId);

    /**
     * Adds a friend with the specified ID and chat ID to the user's friends list.
     *
     * @param friendId The ID of the friend to be added.
     * @param chatId   The chat ID associated with the friend.
     */

    void addFriend(int friendId, int chatId);

    /**
     * Sets a friend status to true for the specified friend ID.
     *
     * @param friendId The ID of the friend to be marked as a friend.
     */

    void makeupFriend(int friendId);

    /**
     * Sets a friend status to false for the specified friend ID.
     *
     * @param friendId The ID of the friend to be removed.
     */

    void removeFriend(int friendId);

    /**
     * Gets the user's friend list as a HashMap containing friend IDs and usernames.
     *
     * @return The HashMap representing the user's friend list (Key: friend ID, Value: friend username).
     */

    HashMap<Integer, String> getFriendList();

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    int getUserId();

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    String getUsername();

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    String getPassword();
}
