package server.data_access;

import server.data_access.local.FileManager;
import server.data_access.network.NetworkManager;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class DataAccess implements TerminalMessageDataAccessInterface, ServerShutdownDataAccessInterface {

    private final NetworkManager networkManager;
    private final FileManager fileManager;
    private final LinkedBlockingQueue<String> terminalMessage = new LinkedBlockingQueue<>();

    public DataAccess() throws IOException {
        fileManager = new FileManager(this);
        networkManager = new NetworkManager(this);
    }

    /**
     * Returns a String to be displayed in the terminal and blocks until data is available.
     * <p>
     * This method retrieves a String to be shown in the terminal and waits until data becomes available.
     *
     * @return The String to be displayed in the terminal.
     */
    @Override
    public String getTerminalMessage() {
        try {
            return terminalMessage.take();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    /**
     * Adds a message to be displayed in the terminal.
     * <p>
     * This method allows you to add a message that will be displayed in the terminal output.
     *
     * @param message The message to be added to the terminal output.
     */
    public void addTerminalMessage(String message) {
        terminalMessage.add(message);
    }

    /**
     * Shuts down data access when called.
     * <p>
     * This method is used to gracefully shut down the program, and it typically includes
     * cleaning up resources and performing any necessary termination procedures.
     */
    public void shutdown() {
        networkManager.shutdown();
    }
}
