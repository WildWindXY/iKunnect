package client.use_case.receive_message;

/**
 * The SendMessageInputBoundary represents an interface for executing the sending
 * of a message to the server.
 * <p>
 * Implementations of this interface are expected to provide a method for executing
 * the process of sending a message using the provided input data.
 */
public interface ReceiveMessageInputBoundary {
    /**
     * Executes the sending of a message based on the provided input data.
     *
     * @param sendMessageInputData The input data for sending the message.
     */
    void execute();
}