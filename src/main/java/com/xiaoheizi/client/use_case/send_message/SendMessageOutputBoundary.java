package com.xiaoheizi.client.use_case.send_message;

/**
 * The SendMessageOutputBoundary represents an interface for presenting the result
 * of sending a message to an external entity.
 * <p>
 * Implementations of this interface are expected to provide a method for presenting
 * the output data resulting from a message send operation.
 */
public interface SendMessageOutputBoundary {
    /**
     * Presents the result of sending a message to an external entity.
     *
     * @param outputData The output data containing the result of the message send operation.
     */
    void presentSendMessageResult(SendMessageOutputData outputData, String message);
}