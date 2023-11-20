import server.data_access.DataAccess;
import server.interface_adapter.TerminalController;
import server.interface_adapter.TerminalPresenter;
import server.interface_adapter.TerminalViewModel;
import server.use_case.friend_request.ServerFriendRequestInteractor;
import server.use_case.get_friend_list.ServerGetFriendListInteractor;
import server.use_case.login.ServerLoginInteractor;
import server.use_case.server_shutdown.ServerShutdownInteractor;
import server.use_case.signup.ServerSignupInteractor;
import server.use_case.terminal_message.TerminalMessageInteractor;
import server.view.TerminalView;
import utils.MessageEncryptionUtils;

import java.io.IOException;

public class IKunnectServer {

    public static void main(String[] args) throws IOException {
        System.out.println("server on!");
        try {
            MessageEncryptionUtils.initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        start();
    }

    public static void start() throws IOException { // No temporary code here
        DataAccess dataAccess = new DataAccess();

        TerminalViewModel terminalViewModel = new TerminalViewModel();
        TerminalPresenter terminalPresenter = new TerminalPresenter(terminalViewModel);

        TerminalMessageInteractor terminalMessageInteractor = new TerminalMessageInteractor(dataAccess, terminalPresenter);
        ServerShutdownInteractor serverShutdownInteractor = new ServerShutdownInteractor(dataAccess, terminalPresenter);
        ServerSignupInteractor serverSignupInteractor = new ServerSignupInteractor(dataAccess, terminalPresenter);
        ServerLoginInteractor serverLoginInteractor = new ServerLoginInteractor(dataAccess, terminalPresenter);
        ServerGetFriendListInteractor serverGetFriendListInteractor = new ServerGetFriendListInteractor(dataAccess, terminalPresenter);
        ServerFriendRequestInteractor serverFriendRequestInteractor = new ServerFriendRequestInteractor(dataAccess, terminalPresenter);

        TerminalView terminalView = new TerminalView(new TerminalController(terminalMessageInteractor, serverShutdownInteractor, serverSignupInteractor, serverLoginInteractor, serverGetFriendListInteractor, serverFriendRequestInteractor), terminalViewModel);
    }
}

