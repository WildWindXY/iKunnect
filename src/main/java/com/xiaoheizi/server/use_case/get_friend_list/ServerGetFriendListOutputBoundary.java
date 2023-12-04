package com.xiaoheizi.server.use_case.get_friend_list;

/**
 * The ServerGetFriendListOutputBoundary interface defines the contract for components that handle
 * the presentation of friend list related messages. Implementations of this interface are responsible
 * for adding messages to the terminal inform users about the get friend list process.
 */
public interface ServerGetFriendListOutputBoundary {
    /**
     * Adds a message related to the get friend list process to the terminal.
     *
     * @param message The message to be added.
     */
    void addMessage(String message);
}
