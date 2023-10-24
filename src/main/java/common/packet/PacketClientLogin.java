package common.packet;

public class PacketClientLogin {
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

}
