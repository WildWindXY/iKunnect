package client.use_case.switch_channels;

import java.nio.channels.Channel;
import java.util.List;

public class ChannelInteractor implements ChannelInputBoundary{
    void createChannel(String name);
    List<Channel> getChannels();
    void switchChannel(String channelId);
    void updateChannel(Channel channel);
    private final ChannelRepository channelRepository;

    public ChannelManagementInteractorImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void createChannel(String name) {
        // Implementation to create a new channel
    }

    @Override
    public List<Channel> getChannels() {
        // Implementation to retrieve channels
    }

    @Override
    public void switchChannel(String channelId) {
        // Implementation to switch channels
    }

    @Override
    public void updateChannel(Channel channel) {
        // Implementation to update a channel
    }
}
