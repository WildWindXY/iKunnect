package com.xiaoheizi.server.use_case.server_shutdown;

public interface ServerShutdownOutputBoundary {
    /**
     * Adds a message to the shutdown output to communicate the shutdown process.
     *
     * @param message The message to be added to the shutdown output to describe the
     *                status or progress of the shutdown.
     */
    void addMessage(String message);
}
