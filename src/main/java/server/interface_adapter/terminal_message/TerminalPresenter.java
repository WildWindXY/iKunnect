package server.interface_adapter.terminal_message;

import server.use_case.terminal_message.TerminalMessageOutputBoundary;

public class TerminalPresenter implements TerminalMessageOutputBoundary {

    private final TerminalViewModel terminalViewModel;

    public TerminalPresenter(TerminalViewModel terminalViewModel) {
        this.terminalViewModel = terminalViewModel;
    }

    /**
     * Adds a message to the queue of terminal messages.
     *
     * @param message The message to be added to the terminal messages queue.
     */
    @Override
    public void addMessage(String message) {
        terminalViewModel.put(message);
    }
}
