package client.use_case.receive_message;

public class ReceiveMessageInteractor implements ReceiveMessageInputBoundary {
    private final ReceiveMessageDataAccessInterface dataAccess;
    private final ReceiveMessageOutputBoundary outputBoundary;

    /**
     * Constructs a SendMessageInteractor with the provided data access and output boundary.
     *
     * @param dataAccess     The data access interface for sending messages.
     * @param outputBoundary The output boundary for presenting message result.
     */
    public ReceiveMessageInteractor(ReceiveMessageDataAccessInterface dataAccess, ReceiveMessageOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Executes the process of sending a message based on the provided input data. It interacts with
     * the data access layer to send the message and communicates the results to the output boundary.
     *
     * @param sendMessageInputData The input data for sending the message, including message content, sender, and receiver.
     */
    @Override
    public void execute() {
        ReceiveMessageOutputData outputData = dataAccess.receiveMessage();
        outputBoundary.presentSendMessageResult(outputData);
    }
}