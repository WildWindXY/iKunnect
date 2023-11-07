package server.interface_adapter;

import server.use_case.terminal_message.TerminalMessageOutputBoundary;

public class TerminalPresenter implements TerminalMessageOutputBoundary {

    private final TerminalViewModel terminalViewModel;

    public TerminalPresenter(TerminalViewModel terminalViewModel) {
        this.terminalViewModel = terminalViewModel;
    }

    @Override
    public void addMessage(String message) {
        terminalViewModel.put(message);
    }
}
