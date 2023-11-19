package common.packet;

import java.util.HashMap;

@SuppressWarnings("record")
public class PacketServerGetFriendListResponse implements Packet {
    private final HashMap<Integer, String> friends;

    public PacketServerGetFriendListResponse(HashMap<Integer, String> friends) {
        this.friends = friends;
    }

    public HashMap<Integer, String> getFriends() {
        return getFriends();
    }
}
