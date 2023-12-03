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
import server.use_case.text_message.ServerTextMessageInteractor;
import server.view.TerminalView;

import java.io.IOException;

public class IKunnectServer {

    private static DataAccess dataAccess;

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException { // No temporary code here
        System.out.println(1);
        DataAccess dataAccess = new DataAccess();
        System.out.println(1);
        TerminalViewModel terminalViewModel = new TerminalViewModel();
        System.out.println(1);
        TerminalPresenter terminalPresenter = new TerminalPresenter(terminalViewModel);
        System.out.println(1);
        TerminalMessageInteractor terminalMessageInteractor = new TerminalMessageInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerShutdownInteractor serverShutdownInteractor = new ServerShutdownInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerSignupInteractor serverSignupInteractor = new ServerSignupInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerLoginInteractor serverLoginInteractor = new ServerLoginInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerGetFriendListInteractor serverGetFriendListInteractor = new ServerGetFriendListInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerFriendRequestInteractor serverFriendRequestInteractor = new ServerFriendRequestInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        ServerTextMessageInteractor serverTextMessageInteractor = new ServerTextMessageInteractor(dataAccess, terminalPresenter);
        System.out.println(1);
        TerminalView terminalView = new TerminalView(new TerminalController(terminalMessageInteractor, serverShutdownInteractor, serverSignupInteractor, serverLoginInteractor, serverGetFriendListInteractor, serverFriendRequestInteractor, serverTextMessageInteractor), terminalViewModel);
        System.out.println(1);
    }
}

