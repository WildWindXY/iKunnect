package server.use_case.signup;

/**
 * The ServerSignupOutputBoundary interface defines the contract for components that handle
 * the presentation of signup-related messages. Implementations of this interface are responsible
 * for adding messages to the terminal inform users about the signup process.
 */
public interface ServerSignupOutputBoundary {
    /**
     * Adds a message related to the signup process to the terminal.
     *
     * @param message The message to be added.
     */
    void addMessage(String message);
}
