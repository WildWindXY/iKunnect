package server.use_case.signup;

import common.packet.Packet;
import common.packet.PacketClientSignup;
import server.data_access.network.ConnectionInfo;
import server.entity.PacketIn;
import server.entity.ServerUser;

/**
 * The ServerSignupDataAccessInterface interface defines the contract for components that
 * handle the data access and management related to client signup operations on the server.
 * Implementations of this interface provide methods for obtaining signup packets, checking
 * username existence, sending packets to clients, and saving new users to local.
 */
public interface ServerSignupDataAccessInterface {
    /**
     * Retrieves a signup packet, and may block until a packet is available.
     *
     * @return The signup packet received from clients.
     * @throws InterruptedException If the thread is interrupted while waiting for a packet.
     */
    PacketIn<PacketClientSignup> getPacketClientSignup() throws InterruptedException;

    /**
     * Checks if a username already exists.
     *
     * @param name The username to check for existence.
     * @return True if the username already exists; otherwise, false.
     */
    boolean usernameExists(String name);

    //TODO: Doc
    void sendTo(Packet packet, ConnectionInfo info);

    /**
     * Adds a new user with the provided username and password to the data source.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return The ServerUser representing the newly added user.
     */
    ServerUser addUser(String username, String password);
}
