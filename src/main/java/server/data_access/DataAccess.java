package server.data_access;

import common.packet.Packet;
import common.packet.PacketClientSignup;
import server.data_access.local.FileManager;
import server.data_access.network.NetworkManager;
import server.entity.PacketIn;
import server.entity.ServerUser;
import server.use_case.server_shutdown.ServerShutdownDataAccessInterface;
import server.use_case.signup.ServerSignupDataAccessInterface;
import server.use_case.terminal_message.TerminalMessageDataAccessInterface;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class DataAccess implements TerminalMessageDataAccessInterface, ServerShutdownDataAccessInterface, ServerSignupDataAccessInterface {

    private final NetworkManager networkManager;
    private final FileManager fileManager;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketIn<PacketClientSignup>> signups = new LinkedBlockingQueue<>();

    public DataAccess() throws IOException {
        fileManager = new FileManager(this);
        networkManager = new NetworkManager(this);
    }

    /**
     * Returns a String to be displayed in the terminal and blocks until data is available.
     * <p>
     * This method retrieves a String to be shown in the terminal and waits until data becomes available.
     *
     * @return The String to be displayed in the terminal.
     */
    @Override
    public String getTerminalMessage() throws InterruptedException {
        return terminalMessage.take();
    }

    public void addPacketClientSignup(PacketIn<PacketClientSignup> packet) {
        signups.add(packet);
    }

    public boolean usernameExists(String name) {
        return fileManager.getUserByUsername(name) != null;
    }

    public ServerUser addUser(String username, String password) {
        return fileManager.addUser(username, password);
    }

    //@Override
    public void sendTo(Packet packet, ServerUser user) {
        networkManager.sendTo(packet, user);
    }

    @Override
    public void sendTo(Packet packet, int id) {
        networkManager.sendTo(packet, id);
    }

    @Override
    public PacketIn<PacketClientSignup> getPacketClientSignup() throws InterruptedException {
        return signups.take();
    }

    /**
     * Adds a message to be displayed in the terminal.
     * <p>
     * This method allows you to add a message that will be displayed in the terminal output.
     *
     * @param message The message to be added to the terminal output.
     */
    public void addTerminalMessage(String message) {
        terminalMessage.add(message);
    }

    /**
     * Shuts down data access when called.
     * <p>
     * This method is used to gracefully shut down the program, and it typically includes
     * cleaning up resources and performing any necessary termination procedures.
     */
    public void shutdown() {
        networkManager.shutdown();
        fileManager.shutdown();
    }

}
