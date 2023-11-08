package server.use_case.server_shutdown;

import server.data_access.ServerShutdownDataAccessInterface;

public class ServerShutdownInteractor implements ServerShutdownInputBoundary {
    private final ServerShutdownDataAccessInterface serverShutdownDataAccessInterface;
    private final ServerShutdownOutputBoundary serverShutdownOutputBoundary;

    public ServerShutdownInteractor(ServerShutdownDataAccessInterface serverShutdownDataAccessInterface, ServerShutdownOutputBoundary serverShutdownOutputBoundary) {
        this.serverShutdownDataAccessInterface = serverShutdownDataAccessInterface;
        this.serverShutdownOutputBoundary = serverShutdownOutputBoundary;
    }

    @Override
    public void shutdown() {
        serverShutdownOutputBoundary.addMessage("Closing DataAccess...");
        serverShutdownDataAccessInterface.shutdown();
        serverShutdownOutputBoundary.addMessage("DataAccess closed successfully.");
    }
}
