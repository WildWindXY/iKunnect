package client.use_case.channel_chats;

import utils.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatsRequestInteractor implements ChatsRequestInputBoundary {
    private ChatsRequestDataAccessInterface dataAccess;
    private ChatsRequestOutputBoundary presenter;

    public ChatsRequestInteractor(ChatsRequestDataAccessInterface dataAccess, ChatsRequestOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ChatsRequestInputData input) {
        // TODO: Fix this method.
//        ChatsRequestOutputData output = dataAccess.requestChats(input);
//        presenter.present(output);
        HashMap<Integer, List<Triple<Long, Integer, String>>> map = new HashMap<>();
        List<Triple<Long, Integer, String>> chats= new ArrayList<>();
        chats.add(new Triple<>(123456789L, 42, "First"));
        chats.add(new Triple<>(987654321L, 24, "Second"));
        map.put(1, chats);
        presenter.present(new ChatsRequestOutputData(map));
    }
}
