package server.interface_adapter;

import server.use_case.get_friend_list.ServerGetFriendListInputBoundary;
import server.use_case.get_friend_list.ServerGetFriendListOutputBoundary;
import server.use_case.login.ServerLoginInputBoundary;
import server.use_case.server_shutdown.ServerShutdownInputBoundary;
import server.use_case.signup.ServerSignupInputBoundary;
import server.use_case.terminal_message.TerminalMessageInputBoundary;

public class TerminalController {

    private final ServerShutdownInputBoundary serverShutdownInputBoundary;

    @SuppressWarnings("unused")
    public TerminalController(TerminalMessageInputBoundary terminalMessageInputBoundary, ServerShutdownInputBoundary serverShutdownInputBoundary, ServerSignupInputBoundary serverSignupInputBoundary, ServerLoginInputBoundary serverLoginInputBoundary, ServerGetFriendListInputBoundary serverGetFriendListInputBoundary) {
        this.serverShutdownInputBoundary = serverShutdownInputBoundary;
    }

    /**
     * Initiates a graceful shutdown of the server or service.
     */
    public void shutdown() {
        serverShutdownInputBoundary.shutdown();
    }
}
