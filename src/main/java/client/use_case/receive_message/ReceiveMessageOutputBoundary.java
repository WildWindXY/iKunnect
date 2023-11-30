package client.use_case.receive_message;

public interface ReceiveMessageOutputBoundary {
    /**
     * Presents the result of receiving a message to the client.
     *
     * @param outputData The output data containing details of the received message.
     */
    void presentSendMessageResult(ReceiveMessageOutputData outputData);
}