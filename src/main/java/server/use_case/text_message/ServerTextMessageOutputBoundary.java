package server.use_case.text_message;

/**
 * The ServerFriendRequestOutputBoundary interface defines the contract for components that handle
 * the presentation of friend request related messages. Implementations of this interface are responsible
 * for adding messages to the terminal inform users about the friend request process.
 */
public interface ServerTextMessageOutputBoundary {
    /**
     * Adds a message related to friend requests to the terminal.
     *
     * @param message The message to be added.
     */
    void addMessage(String message);
}
