package client.view.components.buttons;

import javax.swing.*;
import java.awt.*;

public class LoginSignupButton extends JButton {
    public LoginSignupButton(){
        super();
        Font buttonFont = new Font("Helvetica",Font.PLAIN,20);
        setFont(buttonFont);
        Dimension prefSize = new Dimension(100, 50);
        setPreferredSize(prefSize);
    }
}
