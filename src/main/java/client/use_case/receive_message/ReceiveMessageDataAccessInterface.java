package client.use_case.receive_message;

/**
 * Defines the data access interface for receiving messages.
 */
public interface ReceiveMessageDataAccessInterface {
    /**
     * Receives a message from the data source.
     *
     * @return The output data containing information about the received message.
     */
    ReceiveMessageOutputData receiveMessage();
}