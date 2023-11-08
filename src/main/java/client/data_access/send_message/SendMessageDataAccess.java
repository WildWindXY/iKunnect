package client.data_access.send_message;

import client.data_access.ServerDataAccessObject;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputData;
import client.use_case.SendMessage.SendMessageOutputData;
import common.packet.PacketClientMessage;
import common.packet.PacketServerSendMessageResponse;

import static utils.MessageEncryptionUtils.AES_encrypt;

public class SendMessageDataAccess implements SendMessageDataAccessInterface{
    private final ServerDataAccessObject serverDataAccessObject;

    public SendMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

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
