package server.interface_adapter;

import server.use_case.terminal_message.TerminalMessageInputBoundary;

public class TerminalController {
    private final TerminalMessageInputBoundary terminalMessageInputBoundary;

    public TerminalController(TerminalMessageInputBoundary terminalMessageInputBoundary) {
        this.terminalMessageInputBoundary = terminalMessageInputBoundary;
    }
}
