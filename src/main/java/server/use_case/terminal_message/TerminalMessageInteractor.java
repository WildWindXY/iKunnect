package server.use_case.terminal_message;

import server.data_access.TerminalMessageDataAccessInterface;

public class TerminalMessageInteractor implements TerminalMessageInputBoundary {
    private final TerminalMessageDataAccessInterface terminalMessageDataAccess;
    private final TerminalMessageOutputBoundary terminalMessagePresenter;

    public TerminalMessageInteractor(TerminalMessageDataAccessInterface terminalMessageDataAccess, TerminalMessageOutputBoundary terminalMessagePresenter) {
        this.terminalMessageDataAccess = terminalMessageDataAccess;
        this.terminalMessagePresenter = terminalMessagePresenter;
        new Thread(this::handleMessage).start();
    }

    private void handleMessage() {
        while (true) {
            terminalMessagePresenter.addMessage(terminalMessageDataAccess.getTerminalMessage());
        }
    }
}
