package common.packet;

@SuppressWarnings("record")
public class PacketClientFriendRequest implements Packet {
    private final String username;

    public PacketClientFriendRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
