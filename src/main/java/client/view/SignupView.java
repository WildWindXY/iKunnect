package client.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.intellij.uiDesigner.core.*;

public class SignupView extends JPanel {
    private JPanel SignupMain;

    public SignupView() {
        initComponents();
    }

    public static void main(String[] args) {
        SignupView view = new SignupView();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - unknown
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        loginButton = new JButton();
        Signup = new JButton();
        Cancel = new JButton();
        JPanel panel1 = new JPanel();
        Username = new JTextField();
        Password = new JPasswordField();
        PasswordR = new JPasswordField();

        //======== this ========
        setEnabled(true);
        setBorder(new TitledBorder(new LineBorder(new Color(0x111111)), "Signup", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null, new Color(0x111111)));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
        EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing
        .border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))
        throw new RuntimeException();}});
        setLayout(new GridLayoutManager(7, 2, new Insets(0, 15, 20, 15), -1, -1));

        //---- label1 ----
        label1.setText("Enter Username");
        add(label1, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //---- label2 ----
        label2.setText("Enter Password");
        add(label2, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //---- label3 ----
        label3.setText("Repeat Password");
        add(label3, new GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //---- loginButton ----
        loginButton.setEnabled(true);
        loginButton.setHideActionText(false);
        loginButton.setText("Login");
        add(loginButton, new GridConstraints(5, 0, 1, 2,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //---- Signup ----
        Signup.setEnabled(true);
        Signup.setHideActionText(false);
        Signup.setText("Signup");
        add(Signup, new GridConstraints(4, 0, 1, 2,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK,
            GridConstraints.SIZEPOLICY_FIXED,
            null, new Dimension(178, 30), null));

        //---- Cancel ----
        Cancel.setEnabled(true);
        Cancel.setHideActionText(false);
        Cancel.setText("Cancel");
        add(Cancel, new GridConstraints(6, 0, 1, 2,
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
            panel1.add(Password, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
            panel1.add(PasswordR, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0,
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
    private JButton loginButton;
    private JButton Signup;
    private JButton Cancel;
    private JTextField Username;
    private JPasswordField Password;
    private JPasswordField PasswordR;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
