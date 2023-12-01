package client.interface_adapter.main;

import client.use_case.add_friend.AddFriendInputBoundary;
import client.use_case.high_contrast.HighContrastInputBoundary;
import client.use_case.receive_message.ReceiveMessageInputBoundary;
import client.use_case.send_message.SendMessageInputBoundary;
import client.use_case.send_message.SendMessageInputData;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInputData;
import client.view.MainView;

import javax.swing.*;
import java.awt.*;

public class MainController {

    private final SendMessageInputBoundary sendMessageInteractor;
    private final ReceiveMessageInputBoundary receiveMessageInteractor;
    private final TranslationInputBoundary translationInteractor;
    private final HighContrastInputBoundary highContrastInteractor;

    private final AddFriendInputBoundary addFriendInteractor;
    private final String myUsername;

    private JFrame f;
    private JFrame addFriendInput;

    public MainController(String myUsername, SendMessageInputBoundary sendMessageInteractor, ReceiveMessageInputBoundary receiveMessageInteractor, TranslationInputBoundary translationInteractor, HighContrastInputBoundary highContrastInteractor, AddFriendInputBoundary addFriendInteractor) {
        this.sendMessageInteractor = sendMessageInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
        this.translationInteractor = translationInteractor;
        this.highContrastInteractor = highContrastInteractor;
        this.addFriendInteractor = addFriendInteractor;
        this.myUsername = myUsername;
    }

    public void sendMessage(String message, int recipientID) {
        SendMessageInputData in = new SendMessageInputData(message, myUsername, recipientID);
        sendMessageInteractor.execute(in);
    }

    public void getMessage() {
        receiveMessageInteractor.execute();
    }

    public String translateMessage(String message) {
        TranslationInputData in = new TranslationInputData(message);
        return translationInteractor.execute(in);
    }

    public void openOptionsMenu() {
        if (f == null || !f.isDisplayable()) {
            f = new JFrame("options");
            JPanel p = new JPanel();
            JButton highContrastButton = initHighContrastButton();
            JButton addFriendButton = initAddFriendButton();
            addFriendButton.setPreferredSize(highContrastButton.getPreferredSize());
            p.setLayout(new FlowLayout(FlowLayout.CENTER));
            p.setBackground(MainView.messagesColor);
            p.add(highContrastButton);
            p.add(addFriendButton);
            f.setVisible(true);
            f.add(p);
            f.pack();
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(new Dimension(400, 300));
            f.setLocationRelativeTo(null);
            f.setResizable(false);
        } else {
            f.toFront();
        }

    }

    private JButton initAddFriendButton() {
        JButton b = new JButton("Add Friend");
        Font highContrastUIButtonFont = new Font("Helvetica", Font.BOLD, 24);
        b.setFont(highContrastUIButtonFont);
        b.addActionListener(e -> {
            System.out.println("Add Friend Button");

            if (addFriendInput == null || !addFriendInput.isDisplayable()) {
                addFriendInput = new JFrame("Add Friend");
                JPanel p0 = new JPanel(new GridBagLayout());
                JPanel p = new JPanel();
                //p.setLayout(new GridBagLayout());
                p.setLayout(new FlowLayout());
                p.setBackground(MainView.messagesColor);
                JLabel label = new JLabel("Enter Username");
                label.setFont(highContrastUIButtonFont);
                JTextArea textArea = new JTextArea();
                textArea.setFont(highContrastUIButtonFont);
                textArea.setPreferredSize(new Dimension(200, label.getPreferredSize().height));
                JButton submit = new JButton("Add");
                submit.setFont(highContrastUIButtonFont);
                submit.addActionListener(e1 -> {
                    String s = textArea.getText().strip();
                    textArea.setText("");
                    addFriendInteractor.execute(s);
                    JOptionPane.showMessageDialog(addFriendInput,  // Parent component (null for centering on screen)
                            "Friend Request Sent",  // Message to display
                            "Success",  // Title of the dialog
                            JOptionPane.INFORMATION_MESSAGE);
                });

                p.add(label);
                p.add(textArea);
                p.add(submit);
                p0.add(p);
                p0.setBackground(MainView.messagesColor);
                addFriendInput.setVisible(true);
                addFriendInput.add(p0);
                addFriendInput.pack();
                addFriendInput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                addFriendInput.setSize(new Dimension(600, 200));
                addFriendInput.setLocationRelativeTo(f);
                addFriendInput.setResizable(false);
            } else {
                addFriendInput.toFront();
            }
        });
        return b;
    }

    private JButton initHighContrastButton() {
        JButton b = new JButton("Toggle High Contrast UI");
        Font highContrastUIButtonFont = new Font("Helvetica", Font.BOLD, 24);
        b.setFont(highContrastUIButtonFont);
        b.addActionListener(e -> {
            System.out.println("Toggle High Contrast Button");
            int val = highContrastInteractor.execute(highContrastInteractor.TOGGLE_HIGH_CONTRAST);
            if (val == highContrastInteractor.INVALID_OPTION) {
                System.out.println("MainController -> highContrastInteractor -> invalid action");
            } else {
                boolean highContrast = val == 1;
                System.out.println("Main Controller HC " + highContrast);
                //TODO remove options feature complete
            }
        });
        return b;
    }
}
