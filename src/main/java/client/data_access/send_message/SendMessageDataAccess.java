package client.data_access.send_message;

import client.data_access.ServerDataAccessObject;
import client.use_case.sendMessage.SendMessageDataAccessInterface;
import client.use_case.sendMessage.SendMessageInputData;
import client.use_case.sendMessage.SendMessageOutputData;
import common.packet.PacketClientMessage;
import common.packet.PacketServerSendMessageResponse;

import static utils.MessageEncryptionUtils.AES_encrypt;

public class SendMessageDataAccess implements SendMessageDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public SendMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
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
    public SendMessageOutputData sendMessage(SendMessageInputData input) {
        PacketClientMessage packet = null;
        try {
            packet = new PacketClientMessage(input.getReceiver(), AES_encrypt(input.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        serverDataAccessObject.sendPacket(packet);
        PacketServerSendMessageResponse response = serverDataAccessObject.getSendMessageResponse();
        return new SendMessageOutputData(response.isSuccess(), response.getTimestamp());
    }


}
