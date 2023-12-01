package client.view.exceptions;

/**
 * Custom exception class for invalid message parameters.
 */
public class InvalidMessageException extends IllegalArgumentException {
    public InvalidMessageException(String message) {
        super(message);
    }
}
