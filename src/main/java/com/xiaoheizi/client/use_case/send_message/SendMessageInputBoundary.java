package com.xiaoheizi.client.use_case.send_message;

/**
 * The SendMessageInputBoundary represents an interface for executing the sending
 * of a message to the com.ikun.server.
 * <p>
 * Implementations of this interface are expected to provide a method for executing
 * the process of sending a message using the provided input data.
 */
public interface SendMessageInputBoundary {
    /**
     * Executes the sending of a message based on the provided input data.
     *
     * @param sendMessageInputData The input data for sending the message.
     */
    void execute(SendMessageInputData sendMessageInputData);
}