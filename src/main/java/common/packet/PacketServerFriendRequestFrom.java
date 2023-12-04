package common.packet;

@SuppressWarnings("record")
public class PacketServerFriendRequestFrom implements Packet {
    private final String username;
    private final Type type;

    public PacketServerFriendRequestFrom(String username, Type type) {
        this.username = username;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[PacketServerFriendRequestFrom] username: " + username + ", type: " + type;
    }

    public enum Type {
        REQUEST, ACCEPT
    }
}