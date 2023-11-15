package client.interface_adapter.Login;

import client.interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel extends ViewModel {

    public static final String TITLE_LABEL = "iKunnect - Log In";
    public static final String USERNAME_LABEL = "Enter username";
    public static final String PASSWORD_LABEL = "Enter password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String SIGNUP_BUTTON_TOOLTIPS = "Create new user";

    public static final String LOGIN_BUTTON_LABEL = "Log in";
    public static final String LOGIN_BUTTON_TOOLTIPS = "Log in";
    public static final String EXIT_BUTTON_LABEL = "Exit";
    public static final String EXIT_BUTTON_TOOLTIPS = "Exit the application";

    private LoginState state = new LoginState();

    public LoginViewModel() {
        super("sign up");
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Login Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public LoginState getState() {
        return state;
    }

}
