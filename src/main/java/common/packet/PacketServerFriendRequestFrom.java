package common.packet;

@SuppressWarnings("record")
public class PacketServerFriendRequestFrom implements Packet {
    private final String username;

    public PacketServerFriendRequestFrom(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
