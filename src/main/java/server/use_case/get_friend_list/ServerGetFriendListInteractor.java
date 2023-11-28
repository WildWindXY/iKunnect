package server.use_case.get_friend_list;

import common.packet.PacketClientGetFriendList;
import common.packet.PacketServerGetFriendListResponse;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.use_case.ServerThreadPool;
import utils.TextUtils;
import utils.Triple;
import utils.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ServerGetFriendListInteractor class represents the use case for retrieving a user's friend list.
 */
public class ServerGetFriendListInteractor implements ServerGetFriendListInputBoundary {
    private final ServerGetFriendListOutputBoundary getFriendListPresenter;
    private final ServerGetFriendListDataAccessInterface serverGetFriendListDataAccessInterface;

    /**
     * Constructs a ServerGetFriendListInteractor with the specified data access interface and output boundary.
     *
     * @param serverGetFriendListDataAccessInterface The data access interface for retrieving friend list information.
     * @param getFriendListPresenter                 The output boundary for presenting friend list information.
     */
    public ServerGetFriendListInteractor(ServerGetFriendListDataAccessInterface serverGetFriendListDataAccessInterface, ServerGetFriendListOutputBoundary getFriendListPresenter) {
        this.getFriendListPresenter = getFriendListPresenter;
        this.serverGetFriendListDataAccessInterface = serverGetFriendListDataAccessInterface;
        ServerThreadPool.submit(() -> {
            try {
                while (!Thread.interrupted()) {
                    handlePacket(serverGetFriendListDataAccessInterface.getPacketClientGetFriendList());
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                getFriendListPresenter.addMessage("ServerGetFriendListInteractor ended");
            }
        }, "ServerGetFriendListInteractor");
    }

    /**
     * Handles the incoming packet for getting a friend list and processes the request accordingly.
     *
     * @param packetIn The incoming packet containing the request and connection information.
     */
    private void handlePacket(PacketIn<PacketClientGetFriendList> packetIn) {
        ConnectionInfo info = packetIn.getConnectionInfo();
        try {
            if (packetIn.getConnectionInfo().getStatus() != ConnectionInfo.Status.LOGGED) {
                getFriendListPresenter.addMessage("GetFriendList Failed: connection with id " + info.getConnectionId() + " haven't logged in yet.");
                serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(null, null, PacketServerGetFriendListResponse.Status.NOT_LOGGED_IN), info);
            } else {
                Map<Integer, Integer> friendAndChatIds = info.getUser().getFriendList();
                HashMap<Integer, List<Triple<Long, Integer, String>>> chats = serverGetFriendListDataAccessInterface.getChats(friendAndChatIds.values());
                HashMap<Integer, Tuple<String, Integer>> friends = new HashMap<>();
                for (int friendId : friendAndChatIds.keySet()) {
                    friends.put(friendId, new Tuple<>(serverGetFriendListDataAccessInterface.getUserById(friendId).getUsername(), friendAndChatIds.get(friendId)));
                }
                getFriendListPresenter.addMessage("GetFriendList Success: user " + info.getUser().getUsername() + "'s friend list is " + friends + " with chat list " + chats);
                serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(friends, chats, PacketServerGetFriendListResponse.Status.SUCCESS), info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            getFriendListPresenter.addMessage(TextUtils.error("GetFriendList Failed: " + e.getMessage()));
            serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(null, null, PacketServerGetFriendListResponse.Status.SERVER_ERROR), info);
        }
    }
}
