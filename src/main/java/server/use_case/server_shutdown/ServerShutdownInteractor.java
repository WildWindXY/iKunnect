package server.use_case.server_shutdown;

import server.data_access.ServerShutdownDataAccessInterface;

public class ServerShutdownInteractor implements ServerShutdownInputBoundary {
    private final ServerShutdownDataAccessInterface serverShutdownDataAccessInterface;
    private final ServerShutdownOutputBoundary serverShutdownOutputBoundary;

    public ServerShutdownInteractor(ServerShutdownDataAccessInterface serverShutdownDataAccessInterface, ServerShutdownOutputBoundary serverShutdownOutputBoundary) {
        this.serverShutdownDataAccessInterface = serverShutdownDataAccessInterface;
        this.serverShutdownOutputBoundary = serverShutdownOutputBoundary;
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
        System.exit(0);
    }
}
