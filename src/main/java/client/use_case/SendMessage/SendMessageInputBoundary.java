package client.use_case.SendMessage;

import client.use_case.Signup.SignupInputData;

public interface SendMessageInputBoundary {
    void execute(SendMessageInputData sendMessageInputData);
}