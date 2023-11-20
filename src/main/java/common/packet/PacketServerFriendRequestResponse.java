package common.packet;

import utils.Tuple;

@SuppressWarnings("record")
public class PacketServerFriendRequestResponse implements Packet {
    private final Status status;
    private final Tuple<Integer, String> friendInfo;

    public PacketServerFriendRequestResponse(Tuple<Integer, String> friendInfo, Status status) {
        this.status = status;
        this.friendInfo = friendInfo;
    }

    public Status getStatus() {
        return status;
    }

    public Tuple<Integer, String> getFriendInfo() {
        return friendInfo;
    }

    public enum Status {
        SENT, ACCEPTED, ALREADY_FRIEND
    }
}
