package com.xiaoheizi.client.use_case.add_friend;

import com.xiaoheizi.packet.PacketServerFriendRequestFrom;

import javax.swing.*;

public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendDataAccessInterface dataAccess;
    private final AddFriendOutputBoundary addFriendPresenter;

    public AddFriendInteractor(AddFriendDataAccessInterface dataAccess, AddFriendOutputBoundary addFriendPresenter) {
        this.dataAccess = dataAccess;
        this.addFriendPresenter = addFriendPresenter;
        new Thread(() -> {
            while (true) {
                PacketServerFriendRequestFrom packet = dataAccess.getFriendRequest();
                if (packet.getType() == PacketServerFriendRequestFrom.Type.REQUEST) {
                    Object[] options = {"Accept", "Ignore"};
                    int result = JOptionPane.showOptionDialog(null, "Friend request from " + packet.getUsername() + ", accept?", "Received Friend Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (result == JOptionPane.YES_OPTION) {
                        execute(packet.getUsername());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You are now friend with " + packet.getUsername() + "!", "New Friend", JOptionPane.INFORMATION_MESSAGE);
                    executeFriendList();
                    addFriendPresenter.prepareSuccessView();
                }
            }
        }).start();
    }

    @Override
    public void execute(String username) {
        dataAccess.addFriend(username);
        executeFriendList();
        addFriendPresenter.prepareSuccessView();
    }

    @Override
    public void executeFriendList() {
        addFriendPresenter.friendListSuccessView(dataAccess.getFriendList());
    }
}
