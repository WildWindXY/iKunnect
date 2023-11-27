package client.interface_adapter.Main;

import client.data_access.options.OptionsState;
import client.interface_adapter.Login.LoginState;
import client.interface_adapter.SendMessage.SendMessageState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainViewModel {
    public static final String TITLE_LABEL = "Main Window";

    private SendMessageState sendMessageState = new SendMessageState();

    private LoginState loginState = new LoginState();
    private OptionsState optionsState = new OptionsState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void fireSendMessagePropertyChanged() {
        support.firePropertyChange("sendMessageState", null, this.sendMessageState);
    }

    public void fireLoginPropertyChanged(){
        support.firePropertyChange("loginState", null, this.loginState);
    }

    public void fireOptionsPropertyChanged() {
        System.out.println("MainViewModel fireOptionsPropertyChanged");
        support.firePropertyChange("optionsState", null, this.optionsState);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SendMessageState getSendMessageState() {
        return sendMessageState;
    }

    public LoginState getLoginState(){return this.loginState;}

    public OptionsState getOptionsState(){return optionsState;}
    public String getViewName() {
        return "Main Window";
    }

    public void setSendMessageState(SendMessageState sendMessageState) {
        this.sendMessageState = sendMessageState;
    }

    public void setLoginState(LoginState loginState){this.loginState = loginState;}

    public void setOptionsState(OptionsState optionsState) {this.optionsState = optionsState;}
}
