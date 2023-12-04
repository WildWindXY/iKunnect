package client.view.components.buttons;

import javax.swing.*;
import java.awt.*;

public class loginSignupButton extends JButton {
    public loginSignupButton() {
        super();
        Font buttonFont = new Font("Helvetica", Font.PLAIN, 20);
        setFont(buttonFont);
        Dimension prefSize = new Dimension(100, 50);
        setPreferredSize(prefSize);
    }
}
