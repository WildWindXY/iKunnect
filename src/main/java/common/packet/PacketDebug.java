package common.packet;

public class PacketDebug implements Packet {
    private final String s;

    public PacketDebug(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
