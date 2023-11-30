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
     * Executes the receive-message use case, retrieving received messages from data access
     * and presenting the results using the output boundary.
     */
    @Override
    public void execute() {
        ReceiveMessageOutputData outputData = dataAccess.receiveMessage();
        outputBoundary.presentSendMessageResult(outputData);
    }
}