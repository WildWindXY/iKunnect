package com.xiaoheizi.server.use_case.terminal_message;

/**
 * The TerminalMessageOutputBoundary represents an interface for adding messages to a terminal view.
 * <p>
 * Implementations of this interface are expected to provide a method for adding messages to the
 * terminal view to communicate information, status updates, or other messages to the user.
 */
public interface TerminalMessageOutputBoundary {
    /**
     * Adds a message to the terminal view to communicate information or status updates.
     *
     * @param message The message to be added to the terminal view.
     */
    void addMessage(String message);
}
