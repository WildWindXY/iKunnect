package com.xiaoheizi.client.use_case.receive_message;

public interface ReceiveMessageOutputBoundary {
    /**
     * Presents the result of receiving a message to the com.ikun.client.
     *
     * @param outputData The output data containing details of the received message.
     */
    void presentSendMessageResult(ReceiveMessageOutputData outputData);
}