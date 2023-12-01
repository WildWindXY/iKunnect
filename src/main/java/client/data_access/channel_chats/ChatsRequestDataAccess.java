package client.data_access.channel_chats;

import client.data_access.ServerDataAccessObject;
import client.use_case.channel_chats.ChatsRequestDataAccessInterface;
import client.use_case.channel_chats.ChatsRequestInputData;
import client.use_case.channel_chats.ChatsRequestOutputData;
import common.packet.PacketServerGetFriendListResponse;

public class ChatsRequestDataAccess implements ChatsRequestDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public ChatsRequestDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

    @Override
    public ChatsRequestOutputData requestChats(ChatsRequestInputData input) {
        PacketServerGetFriendListResponse response = serverDataAccessObject.getFriendListResponse();
        try {
            return new ChatsRequestOutputData(response.getChats());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
