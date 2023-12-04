package com.xiaoheizi.client.view.components.popupMenu;

import javax.swing.*;

public class UserIconPopup extends JPopupMenu {
    public UserIconPopup() {
        super();
        JMenuItem viewProfile = new JMenuItem("View Profile");
        JMenuItem sendMessage = new JMenuItem("Send Message");
        JMenuItem addFriend = new JMenuItem("Add Friend");

        add(viewProfile);
        add(sendMessage);
        add(addFriend);
    }
}
