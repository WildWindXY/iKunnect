package server.use_case.signup;

import common.packet.PacketClientSignup;
import common.packet.PacketServerSignupResponse;
import server.entity.PacketIn;
import server.entity.ServerUser;
import server.use_case.ServerThreadPool;

public class ServerSignupInteractor implements ServerSignupInputBoundary {
    private final ServerSignupOutputBoundary serverSignupPresenter;
    private final ServerSignupDataAccessInterface serverSignupDataAccessInterface;

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
        String password = packetIn.getPacket().getPassword();
        int id = packetIn.getConnectionId();
        if (username == null || password == null) {
            serverSignupPresenter.addMessage("Signup Failed: packet contains null attribute.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.NULL_ATTRIBUTE), id);
        } else if (serverSignupDataAccessInterface.usernameExists(username)) {
            serverSignupPresenter.addMessage("Signup Failed: " + username + " exists.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.USERNAME_EXISTS), id);
        } else if (!username.matches("[a-zA-Z0-9]+")) { //isAlphaNumeric
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " contains invalid characters");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.INVALID_CHARACTERS), id);
        } else if (username.length() <= 1) {
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " too short.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.TOO_SHORT), id);
        } else if (username.length() >= 16) {
            serverSignupPresenter.addMessage("Signup Failed: username " + username + " too long.");
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(-1, PacketServerSignupResponse.Status.TOO_LONG), id);
        } else {
            ServerUser user = serverSignupDataAccessInterface.addUser(username, password);
            serverSignupPresenter.addMessage("Signup Success: [username: " + username + ", password: " + password + ", id: " + user.getUserId());
            serverSignupDataAccessInterface.sendTo(new PacketServerSignupResponse(user.getUserId(), PacketServerSignupResponse.Status.SUCCESS), id);
        }
    }
}
