package server.use_case.text_message;

import common.packet.PacketClientTextMessage;
import common.packet.PacketServerTextMessageResponse;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerChats;
import server.entity.ServerMessages;
import server.entity.ServerUser;
import server.use_case.ServerThreadPool;
import utils.TextUtils;

public class ServerTextMessageInteractor implements ServerTextMessageInputBoundary {
    private final ServerTextMessageOutputBoundary textMessagePresenter;
    private final ServerTextMessageDataAccessInterface serverTextMessageDataAccessInterface;

    public ServerTextMessageInteractor(ServerTextMessageDataAccessInterface serverTextMessageDataAccessInterface, ServerTextMessageOutputBoundary textMessagePresenter) {
        this.textMessagePresenter = textMessagePresenter;
        this.serverTextMessageDataAccessInterface = serverTextMessageDataAccessInterface;
        ServerThreadPool.submit(() -> {
            try {
                while (!Thread.interrupted()) {
                    handlePacket(serverTextMessageDataAccessInterface.getPacketClientTextMessage());
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                textMessagePresenter.addMessage("ServerTextMessageInteractor ended");
            }
        }, "ServerTextMessageInteractor");
    }

    private void handlePacket(PacketIn<PacketClientTextMessage> packetIn) {
        final ConnectionInfo info = packetIn.getConnectionInfo();
        final long timestamp = System.currentTimeMillis();
        final String message = packetIn.getPacket().getEncryptedMessage();
        final int clientMessageId = packetIn.getPacket().getClientMessageId();
        final ServerUser user = info.getUser();
        try {
            if (info.getStatus() != ConnectionInfo.Status.LOGGED) {
                textMessagePresenter.addMessage("TextMessage Error: connection with id " + info.getConnectionId() + " haven't logged in yet.");
                serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.NOT_LOGGED_IN), info);
            } else if (message == null || message.trim().isEmpty()) {
                textMessagePresenter.addMessage("TextMessage Error: message from " + user.getUsername() + "is empty.");
                serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.EMPTY_MESSAGE), info);
            } else {
                ServerUser friend = serverTextMessageDataAccessInterface.getUserById(packetIn.getPacket().getRecipient());
                if (friend == null || !user.isFriend(friend.getUserId())) {
                    textMessagePresenter.addMessage("TextMessage Error: recipient id" + packetIn.getPacket().getRecipient() + "in packet from " + user.getUsername() + " is invalid.");
                    serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.NOT_FRIEND), info);
                } else {
                    //TODO
                }
            }
        } catch (Exception e) {
            textMessagePresenter.addMessage(TextUtils.error("FriendRequest Failed: " + e.getMessage()));
            serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.SERVER_ERROR), info);
        } finally {
            ServerChats.save();
            ServerMessages.save();
        }
    }
}