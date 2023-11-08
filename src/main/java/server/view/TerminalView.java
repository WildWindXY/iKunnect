package server.view;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import server.interface_adapter.terminal_message.TerminalController;
import server.interface_adapter.terminal_message.TerminalViewModel;

import java.io.IOException;

public class TerminalView {

    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_SHOW_CONNECTIONS = "connection";
    private final TerminalController terminalController;
    private final TerminalViewModel terminalViewModel;

    public TerminalView(TerminalController terminalController, TerminalViewModel terminalViewModel) throws IOException {
        this.terminalController = terminalController;
        this.terminalViewModel = terminalViewModel;
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        new Thread(this::display).start();
        new Thread(() -> handleInput(terminal)).start();
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

    private void handleInput(Terminal terminal) {
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
        String line;
        while (true) {
            line = reader.readLine("> ");
            if (line.equalsIgnoreCase(COMMAND_EXIT)) {
                break;
            } else if (line.equalsIgnoreCase(COMMAND_SHOW_CONNECTIONS)) {
                //TODO: add use case
            }
        }
    }
}
