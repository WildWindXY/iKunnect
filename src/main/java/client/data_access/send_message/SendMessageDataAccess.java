package client.data_access.send_message;

import client.data_access.ServerDataAccessObject;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputData;
import client.use_case.SendMessage.SendMessageOutputData;
import common.packet.PacketClientTextMessage;
import common.packet.PacketServerTextMessageResponse;

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
        PacketClientTextMessage packet = null;
        try {
            packet = new PacketClientTextMessage(0, Integer.parseInt(input.getReceiver()), AES_encrypt(input.getMessage())); //TODO: Unchecked Integer Cast
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        serverDataAccessObject.sendPacket(packet);
        PacketServerTextMessageResponse response = serverDataAccessObject.getSendMessageResponse();
        return new SendMessageOutputData(response.getStatus() == PacketServerTextMessageResponse.Status.RECEIVED, response.getTimestamp());
    }


}
