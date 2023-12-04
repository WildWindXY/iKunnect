package com.xiaoheizi.server.use_case.login;

/**
 * The ServerLoginOutputBoundary interface defines the contract for components that handle
 * the presentation of login-related messages. Implementations of this interface are responsible
 * for adding messages to the terminal inform users about the login process.
 */
public interface ServerLoginOutputBoundary {
    /**
     * Adds a message related to the login process to the terminal.
     *
     * @param message The message to be added.
     */
    void addMessage(String message);
}
