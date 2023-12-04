package com.xiaoheizi.packet;

public class PacketServerTextMessage implements Packet {
    private final String message;
    private final int senderID;

    private final long timestamp;

    public PacketServerTextMessage(int senderID, String message, long timestamp) {
        this.senderID = senderID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getEncryptedMessage() {
        return message;
    }

    public int getSender() {
        return this.senderID;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return "[PacketServerTextMessage] senderID: " + senderID + ", timestamp: " + timestamp + ", message: " + message;
    }
}
