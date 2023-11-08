package server.data_access;

/**
 * The TerminalMessageDataAccessInterface represents an interface for retrieving terminal messages.
 * <p>
 * Implementations of this interface are expected to provide a method for fetching terminal messages
 * to be displayed to the user on a terminal view.
 */
public interface TerminalMessageDataAccessInterface {
    /**
     * Retrieves a terminal message to be displayed on the terminal view.
     *
     * @return The terminal message to be displayed on the terminal view.
     */
    String getTerminalMessage();
}
