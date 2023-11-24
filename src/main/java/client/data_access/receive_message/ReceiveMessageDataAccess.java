package client.data_access.receive_message;

import client.data_access.ServerDataAccessObject;
import client.use_case.ReceiveMessage.ReceiveMessageDataAccessInterface;
import client.use_case.ReceiveMessage.ReceiveMessageOutputData;
import client.use_case.SendMessage.SendMessageOutputData;
import common.packet.PacketServerMessage;

import static utils.MessageEncryptionUtils.AES_decrypt;

public class ReceiveMessageDataAccess implements ReceiveMessageDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public ReceiveMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

    /**
     * Sends a message based on the provided input data and returns output data.
     * <p>
     * This method sends a message to a server using the input data. It encrypts the message using AES encryption,
     * sends it as a packet to the server using the serverDataAccessObject, and retrieves the response.
     * Finally, it constructs and returns an output data object.
     *
     * @param input The input data for sending the message, including receiver and message content.
     * @return The output data containing the success status and timestamp of the sent message.
     * @throws RuntimeException if an exception occurs during message encryption or transmission.
     */
    @Override
    public ReceiveMessageOutputData receiveMessage() {
        PacketServerMessage response = serverDataAccessObject.getReceiveMessage();
        try {
            return new ReceiveMessageOutputData(response.getSender(), AES_decrypt(response.getEncryptedMessage()), response.getTimestamp());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
