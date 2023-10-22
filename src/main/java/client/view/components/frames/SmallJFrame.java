package client.view.components.frames;

import javax.swing.*;
import java.awt.*;

public class SmallJFrame extends JFrame {

    public SmallJFrame(String title, JPanel view){
        super(title);
        Dimension minSize = new Dimension();
        minSize.setSize(600, 650);
        setResizable(false);
        setMinimumSize(minSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(view);
        pack();
        setVisible(true);
    }
}
