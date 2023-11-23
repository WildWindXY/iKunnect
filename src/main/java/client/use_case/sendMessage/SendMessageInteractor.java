package client.use_case.sendMessage;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageDataAccessInterface dataAccess;
    private final SendMessageOutputBoundary outputBoundary;

    /**
     * Constructs a SendMessageInteractor with the provided data access and output boundary.
     *
     * @param dataAccess     The data access interface for sending messages.
     * @param outputBoundary The output boundary for presenting message result.
     */
    public SendMessageInteractor(SendMessageDataAccessInterface dataAccess, SendMessageOutputBoundary outputBoundary) {
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
    public void execute(SendMessageInputData sendMessageInputData) {
        SendMessageOutputData outputData = dataAccess.sendMessage(sendMessageInputData);
        outputBoundary.presentSendMessageResult(outputData, sendMessageInputData.getMessage());
    }
}