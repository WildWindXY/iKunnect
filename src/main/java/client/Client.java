package client;

import client.view.SignupView;

import javax.swing.*;

public class Client{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Signup Form");
        SignupView view = new SignupView();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }

}