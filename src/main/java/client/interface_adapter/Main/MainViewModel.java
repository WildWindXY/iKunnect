package client.interface_adapter.Main;

import client.interface_adapter.SendMessage.SendMessageState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainViewModel {
    public static final String TITLE_LABEL = "Main Window";

    private SendMessageState state = new SendMessageState();

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SendMessageState getState() {
        return state;
    }
    public String getViewName() {
        return "Main Window";
    }

    public void setState(SendMessageState state) {
        this.state = state;
    }
}
