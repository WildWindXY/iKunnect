package server.data_access;

import common.packet.Packet;
import common.packet.PacketClientLogin;
import common.packet.PacketClientSignup;
import server.data_access.local.FileManager;
import server.data_access.network.ConnectionInfo;
import server.data_access.network.NetworkManager;
import server.entity.PacketIn;
import server.entity.ServerUser;
import server.use_case.login.ServerLoginDataAccessInterface;
import server.use_case.server_shutdown.ServerShutdownDataAccessInterface;
import server.use_case.signup.ServerSignupDataAccessInterface;
import server.use_case.terminal_message.TerminalMessageDataAccessInterface;
import utils.Tuple;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

//TODO: Doc
public class DataAccess implements TerminalMessageDataAccessInterface, ServerShutdownDataAccessInterface, ServerSignupDataAccessInterface, ServerLoginDataAccessInterface {

    private final NetworkManager networkManager;
    private final FileManager fileManager;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketIn<PacketClientSignup>> signups = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketIn<PacketClientLogin>> logins = new LinkedBlockingQueue<>();

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

    public void addPacketClientLogin(PacketIn<PacketClientLogin> packet) {
        logins.add(packet);
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

    public Tuple<Integer, ServerUser> checkPassword(String username, String password) {
        return fileManager.checkPassword(username, password);
    }

    @Override
    public void sendTo(Packet packet, ConnectionInfo info) {
        networkManager.sendTo(packet, info);
    }

    @Override
    public PacketIn<PacketClientSignup> getPacketClientSignup() throws InterruptedException {
        return signups.take();
    }

    @Override
    public PacketIn<PacketClientLogin> getPacketClientLogin() throws InterruptedException {
        return logins.take();
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
