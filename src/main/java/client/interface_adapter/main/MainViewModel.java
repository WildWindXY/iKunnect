package client.interface_adapter.main;

import client.data_access.high_contrast.HighContrastState;
import client.interface_adapter.login.LoginState;
import client.interface_adapter.send_message.SendMessageState;
import utils.Triple;
import utils.Tuple;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;

public class MainViewModel {
    public static final String TITLE_LABEL = "Main Window";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private HashMap<Integer, Tuple<String, Integer>> friends = new HashMap<>();
    private HashMap<Integer, List<Triple<Long, Integer, String>>> chats = new HashMap<>();
    private SendMessageState sendMessageState = new SendMessageState();
    private LoginState loginState = new LoginState();
    private HighContrastState highContrastState = new HighContrastState();

    private int myUserId = -1;

    private int currentChatId = -1;

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void fireSendMessagePropertyChanged() {
        support.firePropertyChange("sendMessageState", null, this.sendMessageState);
    }

    public void fireLoginPropertyChanged() {
        support.firePropertyChange("loginState", null, this.loginState);
    }

    public void fireHighContrastPropertyChanged() {
        System.out.println("MainViewModel fireHighContrastPropertyChanged");
        support.firePropertyChange("highContrastState", null, this.highContrastState);
    }

    public void fireAddFriendPropertyChanged() {
        System.out.println("MainViewModel fireAddFriendPropertyChanged");
        support.firePropertyChange("addFriend", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SendMessageState getSendMessageState() {
        return sendMessageState;
    }

    public void setSendMessageState(SendMessageState sendMessageState) {
        this.sendMessageState = sendMessageState;
    }

    public LoginState getLoginState() {
        return this.loginState;
    }

    public void setLoginState(LoginState loginState) {
        this.loginState = loginState;
    }

    public void initMessages(HashMap<Integer, Tuple<String, Integer>> friends, HashMap<Integer, List<Triple<Long, Integer, String>>> chats) {
        this.friends = friends;
        this.chats = chats;
    }

    public void setFriendList(HashMap<Integer, Tuple<String, Integer>> friends) {
        this.friends = friends;
    }

    public HashMap<Integer, Tuple<String, Integer>> getFriends() {
        return friends;
    }

    public HashMap<Integer, List<Triple<Long, Integer, String>>> getChats() {
        return chats;
    }

    public void setChats(HashMap<Integer, List<Triple<Long, Integer, String>>> chats) {
        this.chats = chats;
    }

    public HighContrastState getOptionsState() {
        return highContrastState;
    }

    public void setOptionsState(HighContrastState highContrastState) {
        this.highContrastState = highContrastState;
    }

    public String getViewName() {
        return "Main Window";
    }

    public String getUserName() {
        return loginState.getUsername();
    }

    public int getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(int myUserId) {
        this.myUserId = myUserId;
    }

    public int getCurrentChatId() {
        return currentChatId;
    }

    public void setCurrentChatId(int currentChatId) {
        this.currentChatId = currentChatId;
    }
}
