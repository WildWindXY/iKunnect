package com.xiaoheizi.server.use_case.server_shutdown;

/**
 * The ServerShutdownInputBoundary represents an interface for initiating a graceful
 * shutdown of the com.ikun.server or service.
 * <p>
 * Implementations of this interface are expected to provide a method for initiating
 * the shutdown process.
 */
public interface ServerShutdownInputBoundary {
    /**
     * Initiates a graceful shutdown of the com.ikun.server or service.
     * Implementations of this method should perform the necessary steps to ensure
     * a smooth and controlled termination.
     */
    void shutdown();
}
