package client.interface_adapter.main;

import client.use_case.add_friend.AddFriendInputBoundary;
import client.use_case.receive_message.ReceiveMessageInputBoundary;
import client.use_case.send_message.SendMessageInputBoundary;
import client.use_case.send_message.SendMessageInputData;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInputData;
import client.use_case.high_contrast.HighContrastInputBoundary;
import client.view.MainView;
import org.apache.logging.log4j.message.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainController {

    private final SendMessageInputBoundary sendMessageInteractor;
    private final ReceiveMessageInputBoundary receiveMessageInteractor;
    private final TranslationInputBoundary translationInteractor;
    private final HighContrastInputBoundary highContrastInteractor;

    private final AddFriendInputBoundary addFriendInteractor;
    private String myUsername;

    private JFrame f;
    private JFrame addFriendInput;

    public MainController(String myUsername, SendMessageInputBoundary sendMessageInteractor, ReceiveMessageInputBoundary receiveMessageInteractor, TranslationInputBoundary translationInteractor, HighContrastInputBoundary highContrastInteractor, AddFriendInputBoundary addFriendInterator) {
        this.sendMessageInteractor = sendMessageInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
        this.translationInteractor = translationInteractor;
        this.highContrastInteractor = highContrastInteractor;
        this.addFriendInteractor = addFriendInterator;
        this.myUsername = myUsername;
    }

    public void sendMessage(String message, String receiver) {
        SendMessageInputData in = new SendMessageInputData(message, myUsername, receiver);
        sendMessageInteractor.execute(in);
    }

    public void getMessage() {
        receiveMessageInteractor.execute();
    }

    public String translateMessage(String message) {
        TranslationInputData in = new TranslationInputData(message);
        return translationInteractor.execute(in);
    }

    public void setMyUsername(String username) {
        this.myUsername = username;
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
            f.setLocationRelativeTo(null);
            f.setSize(new Dimension(400, 300));
            f.setResizable(false);
        } else {f.toFront();}

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
                textArea.setPreferredSize(new Dimension(200,label.getPreferredSize().height));
                JButton submit = new JButton("Add");
                submit.setFont(highContrastUIButtonFont);
                submit.addActionListener(e1 -> {
                    String s = textArea.getText().strip();
                    textArea.setText("");
                    addFriendInteractor.execute(s);
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
                addFriendInput.setLocationRelativeTo(null);
                addFriendInput.setSize(new Dimension(600, 200));
                addFriendInput.setResizable(false);
            } else {addFriendInput.toFront();}
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

    public void switchChannel(String channelName) {
        // Example logic to handle channel switching

        // Fetch messages for the selected channel
        // This is a placeholder logic. You might need to implement the actual message fetching
        // logic based on your application's data model and architecture.
        List<Message> channelMessages = fetchMessagesForChannel(channelName);

        // Update ViewModel with the channel messages
        // This method needs to be implemented in your MainViewModel
        mainViewModel.updateChannelMessages(channelMessages);

        // Optionally, if MainViewModel notifies the view via property change listeners,
        // the view will automatically update to reflect the new channel's messages.
    }

    // A method to fetch messages for a specific channel
    // This is a placeholder and needs to be implemented according to your data access patterns
    private List<Message> fetchMessagesForChannel(String channelName) {
        // Fetch messages for the selected channel
        // This is a placeholder logic. You might need to implement the actual message fetching
        // logic based on your application's data model and architecture.
        List<Message> channelMessages = new ArrayList<>();
    }

}
