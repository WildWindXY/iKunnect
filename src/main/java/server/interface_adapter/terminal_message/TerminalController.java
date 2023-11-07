package server.interface_adapter.terminal_message;

import server.use_case.terminal_message.TerminalMessageInputBoundary;

public class TerminalController {
    @SuppressWarnings({"all"}) //I hate this warning, of course it is unused
    private final TerminalMessageInputBoundary terminalMessageInputBoundary;

    public TerminalController(TerminalMessageInputBoundary terminalMessageInputBoundary) {
        this.terminalMessageInputBoundary = terminalMessageInputBoundary;
    }
}
