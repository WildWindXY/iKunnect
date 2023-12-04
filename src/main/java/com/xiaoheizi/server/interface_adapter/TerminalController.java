package com.xiaoheizi.server.interface_adapter;

import com.xiaoheizi.server.use_case.friend_request.ServerFriendRequestInputBoundary;
import com.xiaoheizi.server.use_case.get_friend_list.ServerGetFriendListInputBoundary;
import com.xiaoheizi.server.use_case.login.ServerLoginInputBoundary;
import com.xiaoheizi.server.use_case.server_shutdown.ServerShutdownInputBoundary;
import com.xiaoheizi.server.use_case.signup.ServerSignupInputBoundary;
import com.xiaoheizi.server.use_case.terminal_message.TerminalMessageInputBoundary;
import com.xiaoheizi.server.use_case.text_message.ServerTextMessageInputBoundary;

public class TerminalController {

    private final ServerShutdownInputBoundary serverShutdownInputBoundary;

    @SuppressWarnings("unused")
    public TerminalController(TerminalMessageInputBoundary terminalMessageInputBoundary, ServerShutdownInputBoundary serverShutdownInputBoundary, ServerSignupInputBoundary serverSignupInputBoundary, ServerLoginInputBoundary serverLoginInputBoundary, ServerGetFriendListInputBoundary serverGetFriendListInputBoundary, ServerFriendRequestInputBoundary serverFriendRequestInputBoundary, ServerTextMessageInputBoundary serverTextMessageInputBoundary) {
        this.serverShutdownInputBoundary = serverShutdownInputBoundary;
    }

    /**
     * Initiates a graceful shutdown of the com.ikun.server or service.
     */
    public void shutdown() {
        serverShutdownInputBoundary.shutdown();
    }
}
