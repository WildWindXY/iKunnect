package common.packet;

public class PacketClientSignup {
    private final String username;
    private final String hashedPassword;

    public PacketClientSignup(String username, String password) {
        this.username = username;
        this.hashedPassword = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.hashedPassword;
    }
}
