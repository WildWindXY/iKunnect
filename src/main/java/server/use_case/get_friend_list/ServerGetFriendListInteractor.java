package server.use_case.get_friend_list;

import common.packet.PacketClientGetFriendList;
import common.packet.PacketServerGetFriendListResponse;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.use_case.ServerThreadPool;
import utils.TextUtils;

import java.util.HashMap;

public class ServerGetFriendListInteractor implements ServerGetFriendListInputBoundary {
    private final ServerGetFriendListOutputBoundary getFriendListPresenter;
    private final ServerGetFriendListDataAccessInterface serverGetFriendListDataAccessInterface;

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

    private void handlePacket(PacketIn<PacketClientGetFriendList> packetIn) {
        ConnectionInfo info = packetIn.getConnectionInfo();
        try {
            if (packetIn.getConnectionInfo().getStatus() != ConnectionInfo.Status.LOGGED) {
                getFriendListPresenter.addMessage("GetFriendList Failed: connection with id " + info.getConnectionId() + " haven't logged in yet.");
                serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(null, PacketServerGetFriendListResponse.Status.NOT_LOGGED_IN), info);
            } else {
                HashMap<Integer, String> map = info.getUser().getFriendList();
                getFriendListPresenter.addMessage("GetFriendList Success: user " + info.getUser().getUsername() + "'s friend list is " + map);
                serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(map, PacketServerGetFriendListResponse.Status.SUCCESS), info);
            }
        } catch (Exception e) {
            getFriendListPresenter.addMessage(TextUtils.error("GetFriendList Failed: " + e.getMessage()));
            serverGetFriendListDataAccessInterface.sendTo(new PacketServerGetFriendListResponse(null, PacketServerGetFriendListResponse.Status.SERVER_ERROR), info);
        }
    }
}
