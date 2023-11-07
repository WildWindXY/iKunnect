package server.data_access;

import server.data_access.network.NetworkManager;

import java.io.IOException;

public class DataAccess implements TerminalMessageDataAccessInterface {

    private final NetworkManager networkManager;

    public DataAccess() throws IOException {
        networkManager = new NetworkManager();
    }

    @Override
    public String getTerminalMessage() {
        return null;
    }
}
