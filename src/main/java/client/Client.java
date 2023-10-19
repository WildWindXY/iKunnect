package client;

import client.view.SignupView;

import javax.swing.*;
import java.awt.*;

public class Client{

    public static void main(String[] args) {
        JFrame frame = new JFrame("iKunnect - Sign up (SignupView)");
        Dimension minSize = new Dimension();
        minSize.setSize(800, 700);
        frame.setResizable(false);
        frame.setMinimumSize(minSize);
        SignupView view = new SignupView();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }

}