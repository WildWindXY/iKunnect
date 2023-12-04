package com.xiaoheizi.client.data_access.send_message;

import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.use_case.send_message.SendMessageDataAccessInterface;
import com.xiaoheizi.client.use_case.send_message.SendMessageInputData;
import com.xiaoheizi.client.use_case.send_message.SendMessageOutputData;
import com.xiaoheizi.packet.PacketClientTextMessage;
import com.xiaoheizi.packet.PacketServerTextMessageResponse;

public class SendMessageDataAccess implements SendMessageDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public SendMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

    /**
     * Sends a message based on the provided input data and returns output data.
     * <p>
     * This method sends a message to a com.ikun.server using the input data. It encrypts the message using AES encryption,
     * sends it as a packet to the com.ikun.server using the serverDataAccessObject, and retrieves the response.
     * Finally, it constructs and returns an output data object.
     *
     * @param input The input data for sending the message, including receiver and message content.
     * @return The output data containing the success status and timestamp of the sent message.
     */
    @Override
    public SendMessageOutputData sendMessage(SendMessageInputData input) {
//        serverDataAccessObject.sendPacket(new PacketClientTextMessage(0, input.getRecipientID(), AES_encrypt(input.getMessage()));
        serverDataAccessObject.sendPacket(new PacketClientTextMessage(0, input.getRecipientID(), input.getMessage()));
        PacketServerTextMessageResponse response = serverDataAccessObject.getSendMessageResponse();
        return new SendMessageOutputData(response.getStatus() == PacketServerTextMessageResponse.Status.RECEIVED, response.getTimestamp(), input.getMessage());
    }
}
