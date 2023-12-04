package com.xiaoheizi.server.use_case.signup;

import com.xiaoheizi.packet.PacketClientSignup;
import com.xiaoheizi.packet.PacketServerSignupResponse;
import com.xiaoheizi.server.data_access.network.ConnectionInfo;
import com.xiaoheizi.server.entity.PacketIn;
import com.xiaoheizi.server.entity.ServerUser;
import com.xiaoheizi.server.use_case.ServerThreadPool;

/**
 * The ServerSignupInteractor class implements the ServerSignupInputBoundary interface
 * and is responsible for handling com.ikun.client signup requests asynchronously. It processes
 * signup packets obtained from the ServerSignupDataAccessInterface and communicates the
 * results to the ServerSignupOutputBoundary. The interactor runs asynchronously in a
 * separate thread, continuously handling signup packets until interrupted.
 */
public class ServerSignupInteractor implements ServerSignupInputBoundary {
    private final ServerSignupOutputBoundary serverSignupPresenter;
    private final ServerSignupDataAccessInterface serverSignupDataAccessInterface;

    /**
     * Constructs a ServerSignupInteractor with the specified data access interface and presenter.
     * The interactor runs asynchronously in a separate thread, continuously handling signup packets
     * until interrupted.
     *
     * @param serverSignupDataAccessInterface The data access interface for com.ikun.client signup data.
     * @param serverSignupPresenter           The presenter for displaying signup-related messages.
     */
    public ServerSignupInteractor(ServerSignupDataAccessInterface serverSignupDataAccessInterface, ServerSignupOutputBoundary serverSignupPresenter) {
        this.serverSignupPresenter = serverSignupPresenter;
        this.serverSignupDataAccessInterface = serverSignupDataAccessInterface;
        ServerThreadPool.submit(() -> {
            try {
                while (!Thread.interrupted()) {
                    handlePacket(serverSignupDataAccessInterface.getPacketClientSignup());
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                serverSignupPresenter.addMessage("ServerSignupInteractor ended");
            }
        }, "ServerSignupInteractor");
    }

    private void handlePacket(PacketIn<PacketClientSignup> packetIn) {
        String username = packetIn.getPacket().getUsername();
        String password = packetIn.getPacket().getHashedPassword();
        ConnectionInfo info = packetIn.getConnectionInfo();
        if (info.getStatus() == ConnectionInfo.Status.LOGGED) {
            serverSignupPresenter.addMessage("Signup Failed: com.ikun.client " + info.getUser().getUsername() + "already logged in");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.ALREADY_LOGGED_IN), info);
        } else if (username == null || password == null) {
            serverSignupPresenter.addMessage("Signup Failed: packet contains null attribute.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.NULL_ATTRIBUTE), info);
        } else if (serverSignupDataAccessInterface.usernameExists(username)) {
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " already exists.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.USERNAME_EXISTS), info);
        } else if (!username.matches("[a-zA-Z0-9]+")) { //isAlphaNumeric
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " contains invalid characters");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.INVALID_CHARACTERS), info);
        } else if (username.length() <= 1) {
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " too short.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.TOO_SHORT), info);
        } else if (username.length() >= 16) {
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " too long.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.TOO_LONG), info);
        } else {
            ServerUser user = serverSignupDataAccessInterface.addUser(username, password);
            serverSignupPresenter.addMessage("Signup Success: [username: " + username + ", password: " + password + ", id: " + user.getUserId() + "]");
            info.login(user);
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(user.getUserId(), PacketServerSignupResponse.Status.SUCCESS), info);
        }
    }
}
