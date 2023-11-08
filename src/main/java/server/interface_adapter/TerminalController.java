package server.interface_adapter;

import server.use_case.server_shutdown.ServerShutdownInputBoundary;
import server.use_case.terminal_message.TerminalMessageInputBoundary;

public class TerminalController {
    @SuppressWarnings({"all"}) //I hate this warning, of course it is unused
    private final TerminalMessageInputBoundary terminalMessageInputBoundary;
    private final ServerShutdownInputBoundary serverShutdownInputBoundary;

    public TerminalController(TerminalMessageInputBoundary terminalMessageInputBoundary, ServerShutdownInputBoundary serverShutdownInputBoundary) {
        this.terminalMessageInputBoundary = terminalMessageInputBoundary;
        this.serverShutdownInputBoundary = serverShutdownInputBoundary;
    }

    /**
     * Initiates a graceful shutdown of the server or service.
     */
    public void shutdown() {
        serverShutdownInputBoundary.shutdown();
    }
}
