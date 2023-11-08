package common.packet;

public class PacketServerSendMessageResponse implements Packet{
    private final long timestamp;
    private final boolean success;

    public PacketServerSendMessageResponse(long timestamp, boolean success){
        this.timestamp = timestamp;
        this.success = success;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public boolean isSuccess(){
        return success;
    }

    @Override
    public String toString(){
        return String.valueOf(timestamp) + " " + String.valueOf(success);
    }
}
