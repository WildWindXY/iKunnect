package server.view;

import server.interface_adapter.TerminalController;
import server.interface_adapter.TerminalViewModel;

public class TerminalView {

    @SuppressWarnings({"all"}) //TODO: I hate this warning
    private final TerminalController terminalController;
    private final TerminalViewModel terminalViewModel;

    public TerminalView(TerminalController terminalController, TerminalViewModel terminalViewModel) {
        this.terminalController = terminalController;
        this.terminalViewModel = terminalViewModel;
        new Thread(this::display).start();
        new Thread(this::handleInput).start();
    }

    private void display() {
        while (true) {
            try {
                System.out.println(terminalViewModel.getMessage());
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void handleInput() {
        //TODO: Terminal input commands
    }
}
