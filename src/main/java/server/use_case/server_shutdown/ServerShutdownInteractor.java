package server.use_case.server_shutdown;

import server.entity.ServerChats;
import server.entity.ServerMessages;
import server.entity.ServerUsers;
import server.use_case.ServerThreadPool;

public class ServerShutdownInteractor implements ServerShutdownInputBoundary {
    private static final Thread shutdownHook = new Thread(() -> {
        System.err.println("Emergency Saving...");
        ServerUsers.save();
        ServerChats.save();
        ServerMessages.save();
        System.err.println("Savings Done.");
    });
    private final ServerShutdownDataAccessInterface serverShutdownDataAccessInterface;
    private final ServerShutdownOutputBoundary serverShutdownOutputBoundary;

    public ServerShutdownInteractor(ServerShutdownDataAccessInterface serverShutdownDataAccessInterface, ServerShutdownOutputBoundary serverShutdownOutputBoundary) {
        this.serverShutdownDataAccessInterface = serverShutdownDataAccessInterface;
        this.serverShutdownOutputBoundary = serverShutdownOutputBoundary;
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * Initiates a graceful shutdown of the application. Closes DataAccess and exits the application with status code 0.
     * <p>
     * This method is used to ensure a proper and controlled termination of the application.
     */
    @Override
    public void shutdown() {
        serverShutdownOutputBoundary.addMessage("Closing DataAccess...");
        serverShutdownDataAccessInterface.shutdown();
        serverShutdownOutputBoundary.addMessage("DataAccess closed successfully.");
        serverShutdownOutputBoundary.addMessage("Closing ServerThreadPool...");
        ServerThreadPool.shutdown();
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
        System.exit(0);
    }
}
