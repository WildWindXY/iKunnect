package server.interface_adapter;

import server.use_case.friend_request.ServerFriendRequestOutputBoundary;
import server.use_case.get_friend_list.ServerGetFriendListOutputBoundary;
import server.use_case.login.ServerLoginOutputBoundary;
import server.use_case.server_shutdown.ServerShutdownOutputBoundary;
import server.use_case.signup.ServerSignupOutputBoundary;
import server.use_case.terminal_message.TerminalMessageOutputBoundary;
import server.use_case.text_message.ServerTextMessageOutputBoundary;

public class TerminalPresenter implements TerminalMessageOutputBoundary, ServerShutdownOutputBoundary, ServerSignupOutputBoundary, ServerLoginOutputBoundary, ServerGetFriendListOutputBoundary, ServerFriendRequestOutputBoundary, ServerTextMessageOutputBoundary {

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
