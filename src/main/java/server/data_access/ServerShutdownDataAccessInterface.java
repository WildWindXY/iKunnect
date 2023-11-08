package server.data_access;

public interface ServerShutdownDataAccessInterface {
    /**
     * Initiates a graceful shutdown of the associated server or service.
     * Implementations of this method should perform any necessary cleanup and
     * resource release to ensure a smooth termination.
     */
    void shutdown();
}
