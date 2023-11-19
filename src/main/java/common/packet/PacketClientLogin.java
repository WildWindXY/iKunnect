package common.packet;

public class PacketClientLogin implements Packet {
    final private String username;
    final private String hashedPassword;

    public PacketClientLogin(String username, String password) {
        this.username = username;
        this.hashedPassword = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    @Override
    public String toString() {
        return "[PacketClientLogin] username: " + username + ", hashedPassword: " + hashedPassword;
    }

}
