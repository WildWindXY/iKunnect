package client.interface_adapter.main;

import client.data_access.high_contrast.HighContrastState;
import client.interface_adapter.login.LoginState;
import client.interface_adapter.send_message.SendMessageState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainViewModel {
    public static final String TITLE_LABEL = "Main Window";

    private SendMessageState sendMessageState = new SendMessageState();

    private LoginState loginState = new LoginState();
    private HighContrastState highContrastState = new HighContrastState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void fireSendMessagePropertyChanged() {
        support.firePropertyChange("sendMessageState", null, this.sendMessageState);
    }

    public void fireLoginPropertyChanged(){
        support.firePropertyChange("loginState", null, this.loginState);
    }

    public void fireHighContrastPropertyChanged() {
        System.out.println("MainViewModel fireHighContrastPropertyChanged");
        support.firePropertyChange("highContrastState", null, this.highContrastState);
    }

    public void fireAddFriendPropertyChanged(){
        System.out.println("MainViewModel fireAddFriendPropertyChanged");
        support.firePropertyChange("addFriend", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SendMessageState getSendMessageState() {
        return sendMessageState;
    }

    public LoginState getLoginState(){return this.loginState;}

    public HighContrastState getOptionsState(){return highContrastState;}
    public String getViewName() {
        return "Main Window";
    }
    public String getUserName() {
        return loginState.getUsername();
    }

    public void setSendMessageState(SendMessageState sendMessageState) {
        this.sendMessageState = sendMessageState;
    }

    public void setLoginState(LoginState loginState){this.loginState = loginState;}

    public void setOptionsState(HighContrastState highContrastState) {this.highContrastState = highContrastState;}
}
