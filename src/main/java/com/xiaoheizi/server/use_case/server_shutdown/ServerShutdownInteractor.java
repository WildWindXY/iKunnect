package com.xiaoheizi.server.use_case.server_shutdown;

import com.xiaoheizi.server.use_case.ServerThreadPool;

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
        serverShutdownOutputBoundary.addMessage("Closing ServerThreadPool...");
        ServerThreadPool.shutdown();
        System.exit(0);
    }
}
