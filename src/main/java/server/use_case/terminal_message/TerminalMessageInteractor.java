package server.use_case.terminal_message;

import server.use_case.ServerThreadPool;

public class TerminalMessageInteractor implements TerminalMessageInputBoundary {
    private final TerminalMessageDataAccessInterface terminalMessageDataAccess;
    private final TerminalMessageOutputBoundary terminalMessagePresenter;

    public TerminalMessageInteractor(TerminalMessageDataAccessInterface terminalMessageDataAccess, TerminalMessageOutputBoundary terminalMessagePresenter) {
        this.terminalMessageDataAccess = terminalMessageDataAccess;
        this.terminalMessagePresenter = terminalMessagePresenter;
        ServerThreadPool.submit(this::handleMessage, "TerminalMessageInteractor");
    }

    private void handleMessage() {
        try {
            while (!Thread.interrupted()) {
                terminalMessagePresenter.addMessage(terminalMessageDataAccess.getTerminalMessage());
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            terminalMessagePresenter.addMessage("TerminalMessageInteractor ended");
        }
    }
}
