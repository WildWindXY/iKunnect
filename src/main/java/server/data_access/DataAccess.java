package server.data_access;

import common.packet.*;
import server.data_access.local.FileManager;
import server.data_access.network.ConnectionInfo;
import server.data_access.network.NetworkManager;
import server.entity.PacketIn;
import server.entity.ServerChat;
import server.entity.ServerUser;
import server.use_case.friend_request.ServerFriendRequestDataAccessInterface;
import server.use_case.get_friend_list.ServerGetFriendListDataAccessInterface;
import server.use_case.login.ServerLoginDataAccessInterface;
import server.use_case.server_shutdown.ServerShutdownDataAccessInterface;
import server.use_case.signup.ServerSignupDataAccessInterface;
import server.use_case.terminal_message.TerminalMessageDataAccessInterface;
import server.use_case.text_message.ServerTextMessageDataAccessInterface;
import utils.Tuple;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

//TODO: Doc
public class DataAccess implements TerminalMessageDataAccessInterface, ServerShutdownDataAccessInterface, ServerSignupDataAccessInterface, ServerLoginDataAccessInterface, ServerGetFriendListDataAccessInterface, ServerFriendRequestDataAccessInterface, ServerTextMessageDataAccessInterface {

    private final NetworkManager networkManager;
    private final FileManager fileManager;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketIn<PacketClientSignup>> signups = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketIn<PacketClientLogin>> logins = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketIn<PacketClientGetFriendList>> getFriendLists = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<PacketIn<PacketClientFriendRequest>> friendRequests = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<PacketIn<PacketClientTextMessage>> textMessages = new LinkedBlockingQueue<>();

    /**
     * Constructs a new DataAccess object, initializing the FileManager and NetworkManager.
     *
     * @throws IOException If an I/O error occurs during the initialization of FileManager or NetworkManager.
     */
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

    /**
     * Adds a login packet to the queue of login packets.
     *
     * @param packet The login packet to be added.
     */
    public void addPacketClientLogin(PacketIn<PacketClientLogin> packet) {
        logins.add(packet);
    }

    public void addPacketClientFriendRequest(PacketIn<PacketClientFriendRequest> packet) {
        friendRequests.add(packet);
    }

    /**
     * Adds a packet to the queue of get friend list packets.
     *
     * @param packet The login packet to be added.
     */
    public void addPacketClientGetFriendList(PacketIn<PacketClientGetFriendList> packet) {
        getFriendLists.add(packet);
    }

    /**
     * Adds a signup packet to the queue of login packets.
     *
     * @param packet The signup packet to be added.
     */
    public void addPacketClientSignup(PacketIn<PacketClientSignup> packet) {
        signups.add(packet);
    }

    public void addPacketClientTextMessage(PacketIn<PacketClientTextMessage> packet) {
        textMessages.add(packet);
    }

    /**
     * Checks if a username already exists in the user database.
     *
     * @param name The username to check for existence.
     * @return True if the username already exists; otherwise, false.
     */
    public boolean usernameExists(String name) {
        return fileManager.getUserByUsername(name) != null;
    }

    public ServerUser getUserByUsername(String username) {
        return fileManager.getUserByUsername(username);
    }

    public ServerUser getUserById(int id) {
        return fileManager.getUserById(id);
    }

    /**
     * Adds a new user with the specified username and password to the user database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The ServerUser representing the newly added user.
     */
    public ServerUser addUser(String username, String password) {
        return fileManager.addUser(username, password);
    }

    /**
     * Checks the password for a given username in the server user database.
     *
     * @param username The username to check for.
     * @param password The password to validate against the stored password.
     * @return A Tuple representing the result of the password check.
     * - If the username and password match, returns Tuple with integer 0 and the corresponding ServerUser.
     * - If the username exists but the password does not match, returns Tuple with integer -1 and null.
     * - If the username does not exist, returns Tuple with integer -2 and null.
     */
    public Tuple<Integer, ServerUser> checkPassword(String username, String password) {
        return fileManager.checkPassword(username, password);
    }

    /**
     * Sends a packet to the specified connection using the ConnectionInfo.
     * If the connection with the given ConnectionInfo is found, the packet is added to the connection's send queue.
     * If the connection is not found, a message is added to the network manager's terminal indicating the unsent packet.
     *
     * @param packet The packet to be sent.
     * @param info   The ConnectionInfo of the target connection.
     */
    @Override
    public void sendTo(Packet packet, ConnectionInfo info) {
        networkManager.sendTo(packet, info);
    }

    /**
     * Gets the incoming signup packet, blocking if no packet is available.
     *
     * @return The PacketIn object containing the signup packet and connection information.
     * @throws InterruptedException If the thread is interrupted while waiting for a signup packet.
     */
    @Override
    public PacketIn<PacketClientSignup> getPacketClientSignup() throws InterruptedException {
        return signups.take();
    }

    @Override
    public PacketIn<PacketClientTextMessage> getPacketClientTextMessage() throws InterruptedException {
        return textMessages.take();
    }

    /**
     * Gets the incoming login packet, blocking if no packet is available.
     *
     * @return The PacketIn object containing the login packet and connection information.
     * @throws InterruptedException If the thread is interrupted while waiting for a login packet.
     */
    @Override
    public PacketIn<PacketClientLogin> getPacketClientLogin() throws InterruptedException {
        return logins.take();
    }

    /**
     * Gets the incoming get friend list packet, blocking if no packet is available.
     *
     * @return The PacketIn object containing the packet and connection information.
     * @throws InterruptedException If the thread is interrupted while waiting for a packet.
     */
    @Override
    public PacketIn<PacketClientGetFriendList> getPacketClientGetFriendList() throws InterruptedException {
        return getFriendLists.take();
    }

    @Override
    public PacketIn<PacketClientFriendRequest> getPacketClientFriendRequests() throws InterruptedException {
        return friendRequests.take();
    }

    @Override
    public ServerChat createChat() {
        return fileManager.createChat();
    }

    @Override
    public ServerChat getChat(int id) {
        return fileManager.getChat(id);
    }

    @Override
    public int addMessage(int senderId, String text) {
        return fileManager.addMessage(senderId, text);
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
