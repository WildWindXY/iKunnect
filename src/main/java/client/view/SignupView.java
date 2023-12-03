package client.view;

import client.interface_adapter.login.LoginViewModel;
import client.interface_adapter.signup.SignupController;
import client.interface_adapter.signup.SignupState;
import client.interface_adapter.signup.SignupViewModel;
import client.use_case.high_contrast.HighContrastOutputData;
import client.view.components.buttons.loginSignupButton;
import client.view.components.labels.InputFieldJLabel;
import client.view.components.textfields.CustomJPasswordField;
import client.view.components.textfields.CustomUsernameJTextField;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import utils.InputUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static client.view.MainView.messagesColor;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "Sign Up";
    JPanel topPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    InputFieldJLabel enterUsernameLabel = new InputFieldJLabel();
    InputFieldJLabel enterPasswordLabel = new InputFieldJLabel();
    InputFieldJLabel repeatPasswordLabel = new InputFieldJLabel();
    CustomUsernameJTextField usernameField = new CustomUsernameJTextField();
    CustomJPasswordField passwordField = new CustomJPasswordField();
    CustomJPasswordField passwordRepeatField = new CustomJPasswordField();
    loginSignupButton signupButton = new loginSignupButton();
    loginSignupButton loginButton = new loginSignupButton();
    loginSignupButton exitButton = new loginSignupButton();
    //private JPanel LoginMain;
    private final StringBuilder usernameBuilder;
    private final StringBuilder passwordBuilder;

    private boolean HC = false;


    public SignupView(SignupController controller, SignupViewModel signupViewModel, HighContrastOutputData outputData) {

        this.HC = HC;
        initComponents(HC);

        signupViewModel.addPropertyChangeListener(this);
        this.usernameBuilder = new StringBuilder();
        this.passwordBuilder = new StringBuilder();


        usernameField.addKeyListener(new KeyAdapter() {
            private final StringBuilder usernameBuilder = SignupView.this.usernameBuilder;
            private boolean deleteFirstChar = false;

            @Override
            public void keyTyped(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (!InputUtils.isValidUsernameChar(typedChar)) {
                    if (InputUtils.isBackspace(typedChar)) {
                        if (usernameField.getCaretPosition() != 0 || deleteFirstChar) {
                            usernameBuilder.deleteCharAt(usernameField.getCaretPosition());
                            deleteFirstChar = false;
                        } else {
                            e.consume();
                            return;
                        }
                    } else if (InputUtils.isDelete(typedChar)) {
                        if (usernameField.getCaretPosition() < usernameBuilder.length()) {
                            usernameBuilder.deleteCharAt(usernameField.getCaretPosition());
                        } else {
                            e.consume();
                            return;
                        }
                    } else {
                        e.consume();
                        return;
                    }
                } else if (usernameBuilder.length() == 20) {
                    e.consume();
                    return;
                } else {
                    usernameBuilder.insert(usernameField.getCaretPosition(), typedChar);
                }
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameBuilder.toString());
                signupViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (typedChar == '\b' && usernameField.getCaretPosition() == 1) {
                    deleteFirstChar = true;
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            private final StringBuilder passwordBuilder = SignupView.this.passwordBuilder;
            private boolean deleteFirstChar = false;

            @Override
            public void keyTyped(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (!InputUtils.isValidPasswordChar(typedChar)) {
                    if (InputUtils.isBackspace(typedChar)) {
                        if (passwordField.getCaretPosition() != 0 || deleteFirstChar) {
                            passwordBuilder.deleteCharAt(passwordField.getCaretPosition());
                            deleteFirstChar = false;
                        } else {
                            e.consume();
                            return;
                        }
                    } else if (InputUtils.isDelete(typedChar)) {
                        if (passwordField.getCaretPosition() < passwordBuilder.length()) {
                            passwordBuilder.deleteCharAt(passwordField.getCaretPosition());
                        } else {
                            e.consume();
                            return;
                        }
                    } else {
                        e.consume();
                        return;
                    }
                } else if (passwordBuilder.length() == 100) {
                    e.consume();
                    return;
                } else {
                    passwordBuilder.insert(passwordField.getCaretPosition(), typedChar);
                }
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(passwordBuilder.toString());
                signupViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (typedChar == '\b' && passwordField.getCaretPosition() == 1) {
                    deleteFirstChar = true;
                }
            }
        });

        passwordRepeatField.addKeyListener(new KeyAdapter() {
            private final StringBuilder passwordBuilder = new StringBuilder();
            private boolean deleteFirstChar = false;

            @Override
            public void keyTyped(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (!InputUtils.isValidPasswordChar(typedChar)) {
                    if (InputUtils.isBackspace(typedChar)) {
                        if (passwordRepeatField.getCaretPosition() != 0 || deleteFirstChar) {
                            passwordBuilder.deleteCharAt(passwordRepeatField.getCaretPosition());
                            deleteFirstChar = false;
                        } else {
                            e.consume();
                            return;
                        }
                    } else if (InputUtils.isDelete(typedChar)) {
                        if (passwordRepeatField.getCaretPosition() < passwordBuilder.length()) {
                            passwordBuilder.deleteCharAt(passwordRepeatField.getCaretPosition());
                        } else {
                            e.consume();
                            return;
                        }
                    } else {
                        e.consume();
                        return;
                    }
                } else if (passwordBuilder.length() == 100) {
                    e.consume();
                    return;
                } else {
                    passwordBuilder.insert(passwordRepeatField.getCaretPosition(), typedChar);
                }
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(passwordBuilder.toString());
                signupViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                final char typedChar = e.getKeyChar();
                if (typedChar == '\b' && passwordRepeatField.getCaretPosition() == 1) {
                    deleteFirstChar = true;
                }
            }
        });

        signupButton.addActionListener(e -> {
            SignupState currentState = signupViewModel.getState();

            String password = signupViewModel.getState().getPassword();
            // Check if the password is valid
            boolean isValidPassword = controller.checkPassword(password);
            String message = isValidPassword ? "Password is valid." : "Password is invalid.";
            // Show the dialog
            JOptionPane.showMessageDialog(null, message, "Password Validation", JOptionPane.INFORMATION_MESSAGE);
            if(isValidPassword){
                controller.execute(
                        currentState.getUsername(),
                        currentState.getPassword(),
                        currentState.getRepeatPassword()
                );
                passwordBuilder.delete(0, passwordBuilder.length());
                passwordField.setText("");
            }
        });


        loginButton.addActionListener(e -> {
            System.out.println("LoginButton Clicked");
            controller.executeLogin();

        });

        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            // Process the user's choice
            if (result == JOptionPane.YES_OPTION) {
                //JOptionPane.showMessageDialog(null, "You clicked Yes!");
                System.exit(0);
            }
        });

    }

    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "Hello.");
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    private void initComponents(boolean HC) {

        //======== Main Window ========

        Font titleFont = new Font("Helvetica", Font.BOLD, 24);

        setEnabled(true);

        setBorder(new TitledBorder(new LineBorder(new Color(0x111111)), SignupViewModel.TITLE_LABEL, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, titleFont, new Color(0x111111)));

        setLayout(new GridLayoutManager(7, 2, new Insets(0, 15, 20, 15), -1, -1));

        //---- signupButton----
        signupButton.setEnabled(true);
        signupButton.setHideActionText(false);
        signupButton.setText(LoginViewModel.SIGNUP_BUTTON_LABEL);
        signupButton.setToolTipText(LoginViewModel.SIGNUP_BUTTON_TOOLTIPS);


        //---- loginButton ----
        loginButton.setEnabled(true);
        loginButton.setHideActionText(false);
        loginButton.setText(LoginViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setToolTipText(LoginViewModel.LOGIN_BUTTON_TOOLTIPS);

        //---- exitButton ----
        exitButton.setEnabled(true);
        exitButton.setHideActionText(false);
        exitButton.setText(LoginViewModel.EXIT_BUTTON_LABEL);
        exitButton.setToolTipText(LoginViewModel.EXIT_BUTTON_TOOLTIPS);


        //---- enterUsernameLabel ----
        enterUsernameLabel.setText(SignupViewModel.USERNAME_LABEL);


        //---- enterPasswordLabel ----
        enterPasswordLabel.setText(SignupViewModel.PASSWORD_LABEL);

        //---- enterPasswordRepeatLabel ----
        repeatPasswordLabel.setText(SignupViewModel.REPEAT_PASSWORD_LABEL);

        //======== topPanel========
        {
            topPanel.setBackground(messagesColor);
            topPanel.setPreferredSize(new Dimension(600, 300));
            topPanel.setLayout(new GridBagLayout());
            topPanel.add(enterUsernameLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            topPanel.add(enterPasswordLabel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            topPanel.add(repeatPasswordLabel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            topPanel.add(usernameField, new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            topPanel.add(passwordField, new GridBagConstraints(1, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            topPanel.add(passwordRepeatField, new GridBagConstraints(1, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        }
        add(topPanel, new GridConstraints(1, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(2, 1), null, null));
        //======== buttonPanel ========
        {
            buttonPanel.setBackground(messagesColor);
            buttonPanel.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
            buttonPanel.add(signupButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            buttonPanel.add(loginButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
            buttonPanel.add(exitButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null));
        }
        setBackground(messagesColor);
        add(buttonPanel, new GridConstraints(4, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(2, 1), null, null));
    }

    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}