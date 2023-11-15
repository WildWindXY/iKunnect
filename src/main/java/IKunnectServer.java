import server.data_access.DataAccess;
import server.interface_adapter.TerminalController;
import server.interface_adapter.TerminalPresenter;
import server.interface_adapter.TerminalViewModel;
import server.use_case.server_shutdown.ServerShutdownInteractor;
import server.use_case.terminal_message.TerminalMessageInteractor;
import server.view.TerminalView;

import java.io.IOException;

import static utils.MessageEncryptionUtils.initKey;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        System.out.println("server on!");
                try {
            initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DataAccess dataAccess = new DataAccess();

        TerminalViewModel terminalViewModel = new TerminalViewModel();
        TerminalPresenter terminalPresenter = new TerminalPresenter(terminalViewModel);

        TerminalMessageInteractor terminalMessageInteractor = new TerminalMessageInteractor(dataAccess, terminalPresenter);
        ServerShutdownInteractor serverShutdownInteractor = new ServerShutdownInteractor(dataAccess, terminalPresenter);

        TerminalView terminalView = new TerminalView(new TerminalController(terminalMessageInteractor, serverShutdownInteractor), terminalViewModel);
    }

}

