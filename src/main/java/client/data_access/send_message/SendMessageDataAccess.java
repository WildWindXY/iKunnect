package client.data_access.send_message;

import client.data_access.ServerDataAccessObject;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputData;
import client.use_case.SendMessage.SendMessageOutputData;
import common.packet.PacketClientMessage;
import common.packet.PacketServerSendMessageResponse;

public class SendMessageDataAccess implements SendMessageDataAccessInterface{
    private final ServerDataAccessObject serverDataAccessObject;

    public SendMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

    @Override
    public SendMessageOutputData sendMessage(SendMessageInputData input) {
        PacketClientMessage packet = new PacketClientMessage(input.getReceiver(), input.getMessage());
        serverDataAccessObject.sendPacket(packet);
        PacketServerSendMessageResponse response = serverDataAccessObject.getSendMessageResponse();
        return new SendMessageOutputData(response.isSuccess(), response.getTimestamp());
    }


}
