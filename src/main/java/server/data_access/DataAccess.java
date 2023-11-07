package server.data_access;

import server.data_access.network.NetworkManager;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class DataAccess implements TerminalMessageDataAccessInterface {

    private final NetworkManager networkManager;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    public DataAccess() throws IOException {
        networkManager = new NetworkManager(this);
    }

    @Override
    public String getTerminalMessage() {
        try {
            return terminalMessage.take();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public void addTerminalMessage(String message) {
        terminalMessage.add(message);
    }

    public void shutdown() {
        networkManager.shutdown();
    }
}
