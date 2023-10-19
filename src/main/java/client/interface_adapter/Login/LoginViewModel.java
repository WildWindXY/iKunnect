package client.interface_adapter.Login;

import client.interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class LoginViewModel extends ViewModel {

    public final String TITLE_LABEL = "Log In View";
    public final String USERNAME_LABEL = "Enter username";
    public final String PASSWORD_LABEL = "Enter password";

    public final String LOGIN_BUTTON_LABEL = "Log in";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    public LoginViewModel() {
        super("Login");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
