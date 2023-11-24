package client.view.components.textfields;

import client.interface_adapter.Signup.SignupState;
import utils.InputUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomUsernameJTextField extends JTextField {

    public CustomUsernameJTextField() {
        super();
        Dimension prefSize = new Dimension(200, 30);
        setPreferredSize(prefSize);

        enableInputMethods(true);

        ActionMap usernameFieldActionMap = getActionMap();
        usernameFieldActionMap.put("copy-to-clipboard", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Do nothing on copy action
                System.out.println("attempted to copy");
            }

        });

        usernameFieldActionMap.put("paste-from-clipboard", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Do nothing on paste action
                System.out.println("attempted to paste");
            }
        });

        usernameFieldActionMap.put("cut-to-clipboard", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Do nothing on cut action
            }
        });
    }
}
