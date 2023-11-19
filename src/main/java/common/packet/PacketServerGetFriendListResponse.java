package common.packet;

import java.util.HashMap;

@SuppressWarnings("record")
public class PacketServerGetFriendListResponse implements Packet {
    private final HashMap<Integer, String> friends;
    private final Status status;

    public PacketServerGetFriendListResponse(HashMap<Integer, String> friends, Status status) {
        this.friends = friends;
        this.status = status;
    }

    public HashMap<Integer, String> getFriends() {
        return friends;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        SUCCESS, NOT_LOGGED_IN, SERVER_ERROR
    }
}
