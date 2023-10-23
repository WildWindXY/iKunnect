package client.view.components.frames;

import javax.swing.*;
import java.awt.*;

public class SmallJFrame extends JFrame {

    public SmallJFrame(String title, JPanel view){
        super(title);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(view);
        pack();
        setVisible(true);
        Dimension windowSize = new Dimension();
        windowSize.setSize(550, 650);
        setSize(windowSize);
    }
}
