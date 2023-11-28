package client.interface_adapter.signup;

import client.interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SignupViewModel extends ViewModel {

    public static final String TITLE_LABEL = "iKunnect - Sign Up";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String PASSWORD_LABEL = "Choose password";
    public static final String REPEAT_PASSWORD_LABEL = "Repeat password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign up";
    public static final String SIGNUP_BUTTON_TOOLTIPS = "Create new user";

    public static final String LOGIN_BUTTON_LABEL = "Log in";
    public static final String LOGIN_BUTTON_TOOLTIPS = "Switch to login";
    public static final String EXIT_BUTTON_LABEL = "Exit";
    public static final String EXIT_BUTTON_TOOLTIPS = "Exit the application";

    private SignupState state = new SignupState();

    public SignupViewModel() {
        super("Sign Up");
    }

    public void setState(SignupState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SignupState getState() {
        return state;
    }

}
