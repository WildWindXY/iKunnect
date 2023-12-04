package com.xiaoheizi.server.use_case.friend_request;

import com.xiaoheizi.packet.PacketClientFriendRequest;
import com.xiaoheizi.packet.PacketServerFriendRequestFrom;
import com.xiaoheizi.packet.PacketServerFriendRequestResponse;
import com.xiaoheizi.server.data_access.network.ConnectionInfo;
import com.xiaoheizi.server.entity.*;
import com.xiaoheizi.server.use_case.ServerThreadPool;
import com.xiaoheizi.utils.TextUtils;
import com.xiaoheizi.utils.Triple;

/**
 * The ServerFriendRequestInteractor class handles com.ikun.client friend request packets.
 */
public class ServerFriendRequestInteractor implements ServerFriendRequestInputBoundary {
    private final ServerFriendRequestOutputBoundary friendRequestPresenter;
    private final ServerFriendRequestDataAccessInterface serverFriendRequestDataAccessInterface;

    /**
     * Constructs a ServerFriendRequestInteractor with the given data access interface and presenter.
     *
     * @param serverFriendRequestDataAccessInterface The data access interface for friend request-related operations.
     * @param friendRequestPresenter                 The presenter for friend request-related output.
     */
    public ServerFriendRequestInteractor(ServerFriendRequestDataAccessInterface serverFriendRequestDataAccessInterface, ServerFriendRequestOutputBoundary friendRequestPresenter) {
        this.friendRequestPresenter = friendRequestPresenter;
        this.serverFriendRequestDataAccessInterface = serverFriendRequestDataAccessInterface;
        ServerThreadPool.submit(() -> {
            try {
                while (!Thread.interrupted()) {
                    handlePacket(serverFriendRequestDataAccessInterface.getPacketClientFriendRequests());
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                friendRequestPresenter.addMessage("ServerFriendRequestInteractor ended");
            }
        }, "ServerFriendRequestInteractor");
    }

    /**
     * Handles the incoming friend request packet.
     *
     * @param packetIn The incoming friend request packet.
     */
    private void handlePacket(PacketIn<PacketClientFriendRequest> packetIn) {
        ConnectionInfo info = packetIn.getConnectionInfo();
        try {
            if (packetIn.getConnectionInfo().getStatus() != ConnectionInfo.Status.LOGGED) {
                friendRequestPresenter.addMessage("FriendRequest Failed: connection with id " + info.getConnectionId() + " haven't logged in yet.");
                serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(null, PacketServerFriendRequestResponse.Status.NOT_LOGGED_IN), info);
            } else {
                ServerUser user = info.getUser();
                ServerUser friend = serverFriendRequestDataAccessInterface.getUserByUsername(packetIn.getPacket().getUsername());
                if (friend == null || user.getUsername().equals(friend.getUsername())) {
                    friendRequestPresenter.addMessage("FriendRequest Failed: friend request to " + packetIn.getPacket().getUsername() + " by " + user.getUsername() + " is invalid.");
                    serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(null, PacketServerFriendRequestResponse.Status.INFO_INVALID), info);
                } else if (user.isFriend(friend.getUserId()) != friend.isFriend(user.getUserId())) {
                    throw new IllegalStateException("How " + user.getUsername() + " and " + friend.getUsername() + " are both friend and not friend?");
                } else if (user.isFriend(friend.getUserId())) {
                    friendRequestPresenter.addMessage("FriendRequest Failed: " + user.getUsername() + " and " + friend.getUsername() + " already established friendship.");
                    serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(null, PacketServerFriendRequestResponse.Status.ALREADY_FRIEND), info);
                } else if (user.wasFriend(friend.getUserId()) != friend.wasFriend(user.getUserId())) {
                    throw new IllegalStateException("How " + user.getUsername() + " and " + friend.getUsername() + " are both ex-friend and not ex-friend?");
                } else if (user.wasFriend(friend.getUserId())) {
                    if (user.isFriendRequestedBy(friend.getUserId())) {
                        user.removeFriendRequest(friend.getUserId());
                        user.makeupFriend(friend.getUserId());
                        friend.makeupFriend(user.getUserId());
                        friendRequestPresenter.addMessage("FriendRequest Success: " + user.getUsername() + " and " + friend.getUsername() + " made up their friendship.");
                        serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(new Triple<>(friend.getUserId(), friend.getUsername(), user.getChatId(friend.getUserId())), PacketServerFriendRequestResponse.Status.ACCEPTED), info);
                        ConnectionInfo friendInfo = serverFriendRequestDataAccessInterface.getConnectionInfo(friend.getUserId());
                        if (friendInfo != null) {
                            serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestFrom(user.getUsername(), PacketServerFriendRequestFrom.Type.ACCEPT), friendInfo);
                        }
                    }
                } else if (user.isFriendRequestedBy(friend.getUserId())) {
                    user.removeFriendRequest(friend.getUserId());
                    ServerChat chat = serverFriendRequestDataAccessInterface.createChat();
                    user.addFriend(friend.getUserId(), chat.getChatId());
                    friend.addFriend(user.getUserId(), chat.getChatId());
                    friendRequestPresenter.addMessage("FriendRequest Success: " + user.getUsername() + " and " + friend.getUsername() + " become friends.");
                    serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(new Triple<>(friend.getUserId(), friend.getUsername(), chat.getChatId()), PacketServerFriendRequestResponse.Status.ACCEPTED), info);
                    ConnectionInfo friendInfo = serverFriendRequestDataAccessInterface.getConnectionInfo(friend.getUserId());
                    if (friendInfo != null) {
                        serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestFrom(user.getUsername(), PacketServerFriendRequestFrom.Type.ACCEPT), friendInfo);
                    }
                } else {
                    sendRequest(info, user, friend);
                }
            }
        } catch (Exception e) {
            friendRequestPresenter.addMessage(TextUtils.error("FriendRequest Failed: " + e.getMessage()));
            serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(null, PacketServerFriendRequestResponse.Status.SERVER_ERROR), info);
        } finally {
            ServerUsers.save();
            ServerChats.save();
        }
    }

    private void sendRequest(ConnectionInfo info, ServerUser user, ServerUser friend) {
        friend.addFriendRequest(user.getUserId());
        friendRequestPresenter.addMessage("FriendRequest Sent: from " + user.getUsername() + " to " + friend.getUsername() + ".");
        serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestResponse(null, PacketServerFriendRequestResponse.Status.SENT), info);
        ConnectionInfo friendInfo = serverFriendRequestDataAccessInterface.getConnectionInfo(friend.getUserId());
        if (friendInfo != null) {
            serverFriendRequestDataAccessInterface.sendTo(new PacketServerFriendRequestFrom(user.getUsername(), PacketServerFriendRequestFrom.Type.REQUEST), friendInfo);
        }
    }
}
