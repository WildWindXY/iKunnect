package client.view;

import javax.swing.*;
public class JDialogPopup {

    public JDialogPopup(){

    }
        public static void main(String[] args) {
            // Show a simple information message dialog
            //JOptionPane.showMessageDialog(null, "This is a pop-up window!", "Information", JOptionPane.INFORMATION_MESSAGE);

            // Show a confirmation dialog with yes/no options
            int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);

            // Process the user's choice
            if (result == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "You clicked Yes!");
            } else {
                JOptionPane.showMessageDialog(null, "You clicked No or closed the dialog.");
            }
        }
    }

