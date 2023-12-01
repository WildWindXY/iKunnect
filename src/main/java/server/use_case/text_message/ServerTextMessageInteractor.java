package server.use_case.text_message;

import common.packet.PacketClientTextMessage;
import common.packet.PacketServerTextMessage;
import common.packet.PacketServerTextMessageResponse;
import server.data_access.network.ConnectionInfo;
import server.entity.*;
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
                textMessagePresenter.addMessage("TextMessage Error: message from " + user.getUsername() + "is invalid.");
                serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.INVALID_MESSAGE), info);
            } else {
                ServerUser friend = serverTextMessageDataAccessInterface.getUserById(packetIn.getPacket().getRecipient());
                if (friend == null || !user.isFriend(friend.getUserId())) {
                    textMessagePresenter.addMessage("TextMessage Error: recipient id " + packetIn.getPacket().getRecipient() + " in packet from " + user.getUsername() + " is invalid.");
                    serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.NOT_FRIEND), info);
                } else {
                    int chatId = user.getChatId(friend.getUserId());
                    if (chatId != friend.getChatId(user.getUserId())) {
                        throw new IllegalStateException("How " + user.getUsername() + " and " + friend.getUsername() + " have different chatIds?");
                    } else {
                        ServerChat chat = serverTextMessageDataAccessInterface.getChat(chatId);
                        if (chat == null) {
                            throw new IllegalStateException("How chat with id " + chatId + " is null?");
                        } else {
                            int messageId = serverTextMessageDataAccessInterface.addMessage(user.getUserId(), message);
                            chat.addMessage(messageId);
                            textMessagePresenter.addMessage("TextMessage from " + user.getUsername() + " to " + friend.getUsername() + ": " + message);
                            serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessageResponse(clientMessageId, timestamp, PacketServerTextMessageResponse.Status.RECEIVED), info);
                            ConnectionInfo friendInfo = serverTextMessageDataAccessInterface.getConnectionInfo(friend.getUserId());
                            if (friendInfo != null) {
                                textMessagePresenter.addMessage("TextMessage from " + user.getUsername() + " to " + friend.getUsername() + " sent successfully");
                                serverTextMessageDataAccessInterface.sendTo(new PacketServerTextMessage(user.getUserId(), message, timestamp), friendInfo);
                            }else {
                                textMessagePresenter.addMessage("TextMessage to " + friend.getUsername() +" unsent since not online.");
                            }
                        }
                    }
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