package client.view.components.buttons;

import javax.swing.*;
import java.awt.*;

public class CustomJButton extends JButton {
    public CustomJButton(){
        super();
        Font buttonFont = new Font("Helvetica",Font.PLAIN,20);
        setFont(buttonFont);
        Dimension prefSize = new Dimension(200, 30);
        setPreferredSize(prefSize);
    }
}
