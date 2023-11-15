package client.view.components.popupMenu;

import javax.swing.*;

public class ChannelPopup extends JPopupMenu {

    public ChannelPopup() {
        super();

        JMenuItem renameChannel = new JMenuItem("Rename Channel");
        JMenuItem hideChannel = new JMenuItem("Hide Channel");
        JMenuItem leaveChannel = new JMenuItem("Leave Channel");

        add(renameChannel);
        add(hideChannel);
        add(leaveChannel);
    }
}