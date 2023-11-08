package client.use_case.SendMessage;

import client.use_case.Translate.TranslationOutputData;

public interface SendMessageOutputBoundary {
    void presentSendMessageResult(SendMessageOutputData outputData);
}