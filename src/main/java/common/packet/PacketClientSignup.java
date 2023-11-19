package common.packet;

/**
 * The PacketClientSignup class represents a client packet for signup requests.
 * It includes information about the username and hashed password for the signup operation.
 */
public class PacketClientSignup implements Packet {
    private final String username;
    private final String hashedPassword;

    /**
     * Constructs a PacketClientSignup with the specified username and hashed password.
     *
     * @param username       The username for the signup request.
     * @param hashedPassword The hashed password for the signup request.
     */
    public PacketClientSignup(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets the username associated with the signup request.
     *
     * @return The username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the hashed password associated with the signup request.
     *
     * @return The hashed password.
     */
    public String getPassword() {
        return this.hashedPassword;
    }
}
