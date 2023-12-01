package client.use_case.channel_chats;

public class ChatsRequestInputData {
    private String channelName;

    public ChatsRequestInputData(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

}
