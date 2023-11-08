import com.google.gson.Gson;
import server.data_access.DataAccess;
import server.data_access.TerminalMessageDataAccessInterface;
import server.interface_adapter.terminal_message.TerminalController;
import server.interface_adapter.terminal_message.TerminalPresenter;
import server.interface_adapter.terminal_message.TerminalViewModel;
import server.use_case.terminal_message.TerminalMessageInteractor;
import server.view.TerminalView;

import java.io.IOException;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        DataAccess dataAccess = new DataAccess();
        initTerminalMessage(dataAccess);
    }

    private static void initTerminalMessage(TerminalMessageDataAccessInterface terminalMessageDataAccess) throws IOException {
        TerminalViewModel terminalViewModel = new TerminalViewModel();
        TerminalPresenter terminalPresenter = new TerminalPresenter(terminalViewModel);
        TerminalMessageInteractor terminalMessageInteractor = new TerminalMessageInteractor(terminalMessageDataAccess, terminalPresenter);
        TerminalView terminalView = new TerminalView(new TerminalController(terminalMessageInteractor), terminalViewModel);
    }
}
