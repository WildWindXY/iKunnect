package client.use_case.sendMessage;

/**
 * The SendMessageDataAccessInterface represents an interface for sending messages
 * with input and output data.
 * <p>
 * Implementations of this interface are expected to provide a method for sending messages
 * based on the input data provided and returning output data in response.
 */
public interface SendMessageDataAccessInterface {
    /**
     * Sends a message based on the provided input data and returns output data.
     *
     * @param input The input data for sending the message.
     * @return The output data in response to the sent message.
     */
    SendMessageOutputData sendMessage(SendMessageInputData input);
}