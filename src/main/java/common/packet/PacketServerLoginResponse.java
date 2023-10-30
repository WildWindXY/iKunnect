package common.packet;

public class PacketServerLoginResponse {
    private final long userID;
    private final boolean success;

    public PacketServerLoginResponse(long userID, boolean success){
        this.userID = userID;
        this.success = success;
    }

    public long getUserID(){
        return userID;
    }

    public boolean isSuccess(){
        return success;
    }
}
