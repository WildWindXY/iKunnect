package client.use_case.channel_chats;

import org.apache.logging.log4j.message.Message;
import utils.Triple;

import java.util.HashMap;
import java.util.List;

public class ChatsRequestOutputData {
    public HashMap<Integer, List<Triple<Long, Integer, String>>> chats;
    public ChatsRequestOutputData(HashMap<Integer, List<Triple<Long, Integer, String>>> chats) {
        this.chats = chats;
    }
    public HashMap<Integer, List<Triple<Long, Integer, String>>> getChats() {
        return chats;
    }
}
