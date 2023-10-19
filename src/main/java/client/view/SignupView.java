package client.view;

import client.interface_adapter.Signup.SignupViewModel;

import client.view.components.buttons.*;
import client.view.components.textfields.*;
import client.view.components.labels.*;


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    //private JPanel SignupMain;
    private final SignupViewModel signupViewModel;

    private JLabel enterUsernameLabel;
    private JLabel enterPasswordLabel;
    private JLabel repeatPasswordLabel;
    private CustomJButton loginButton;
    private CustomJButton signupButton;
    private CustomJButton exitButton;
    private CustomJTextField Username;
    private CustomJPasswordField passwordField;
    private CustomJPasswordField passwordFieldRepeat;

    public SignupView() {
        initComponents();
        this.signupViewModel = new SignupViewModel();
    }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - unknown
        enterUsernameLabel = new InputFieldJLabel();
        enterPasswordLabel = new InputFieldJLabel();
        repeatPasswordLabel = new InputFieldJLabel();
        loginButton = new CustomJButton();
        signupButton = new CustomJButton();
        exitButton = new CustomJButton();
        JPanel panel1 = new JPanel();
        Username = new CustomJTextField();
        passwordField = new CustomJPasswordField();
        passwordFieldRepeat = new CustomJPasswordField();


        //======== this ========

        Font titleFont = new Font("Helvetica",Font.BOLD,24);
        Font buttonFont = new Font("Helvetica",Font.PLAIN,16);


        setEnabled(true);

        setBorder(new TitledBorder(new LineBorder(new Color(0x111111)), "iKunnect", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, titleFont, new Color(0x111111)));

        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });
        setLayout(new GridLayoutManager(7, 2, new Insets(0, 15, 20, 15), -1, -1));

        //---- enterUsernameLabel ----
        enterUsernameLabel.setText("Enter Username");
        add(enterUsernameLabel, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

        //---- enterPasswordLabel ----
        enterPasswordLabel.setText("Enter Password");
        add(enterPasswordLabel, new GridConstraints(2, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

        //---- repeatPasswordLabel ----
        repeatPasswordLabel.setText("Repeat Password");
        add(repeatPasswordLabel, new GridConstraints(3, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

        //---- loginButton ----
        loginButton.setEnabled(true);
        loginButton.setHideActionText(false);
        loginButton.setText("Login");
        loginButton.setFont(buttonFont);
        add(loginButton, new GridConstraints(5, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

        //---- Signup ----
        signupButton.setEnabled(true);
        signupButton.setHideActionText(false);
        signupButton.setText("Signup");
        signupButton.setFont(buttonFont);
        add(signupButton, new GridConstraints(4, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                GridConstraints.SIZEPOLICY_FIXED,
                null, new Dimension(178, 30), null));

        //---- Cancel ----
        exitButton.setEnabled(true);
        exitButton.setHideActionText(false);
        exitButton.setText("Cancel");
        exitButton.setFont(buttonFont);
        add(exitButton, new GridConstraints(6, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

        //======== panel1 ========
        {
            panel1.setLayout(new GridBagLayout());
            panel1.add(Username, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            panel1.add(passwordField, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            panel1.add(passwordFieldRepeat, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        add(panel1, new GridConstraints(1, 1, 3, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                new Dimension(2, 1), null, null));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - unknown

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}