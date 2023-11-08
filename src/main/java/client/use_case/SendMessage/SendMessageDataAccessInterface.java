package client.use_case.SendMessage;

public interface SendMessageDataAccessInterface {
    SendMessageOutputData sendMessage(SendMessageInputData input);
}