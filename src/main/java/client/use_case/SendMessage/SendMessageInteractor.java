package client.use_case.SendMessage;

public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageDataAccessInterface dataAccess;
    private final SendMessageOutputBoundary outputBoundary;
    public SendMessageInteractor(SendMessageDataAccessInterface dataAccess, SendMessageOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(SendMessageInputData sendMessageInputData) {
        SendMessageOutputData outputData = dataAccess.sendMessage(sendMessageInputData);
        outputBoundary.presentSendMessageResult(outputData);
    }
}