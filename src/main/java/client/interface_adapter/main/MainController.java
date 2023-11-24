package client.interface_adapter.main;

import client.use_case.options.OptionsInputBoundary;
import client.use_case.receiveMessage.ReceiveMessageInputBoundary;
import client.use_case.sendMessage.SendMessageInputBoundary;
import client.use_case.sendMessage.SendMessageInputData;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInputData;
import client.view.MainView;

import javax.swing.*;
import java.awt.*;

public class MainController {

    private final SendMessageInputBoundary sendMessageInteractor;
    private final ReceiveMessageInputBoundary receiveMessageInteractor;
    private final TranslationInputBoundary translationInteractor;
    private final OptionsInputBoundary optionsInteractor;
    private final String myUsername;

    public MainController(String myUsername, SendMessageInputBoundary sendMessageInteractor, ReceiveMessageInputBoundary receiveMessageInteractor, TranslationInputBoundary translationInteractor, OptionsInputBoundary optionsInteractor){
        this.sendMessageInteractor = sendMessageInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
        this.translationInteractor = translationInteractor;
        this.optionsInteractor = optionsInteractor;
        this.myUsername = myUsername;
    }

    public void sendMessage(String message, String receiver){
        SendMessageInputData in = new SendMessageInputData(message, myUsername, receiver);
        sendMessageInteractor.execute(in);
    }

    public void getMessage(){
        receiveMessageInteractor.execute();
    }

    public String translateMessage(String message){
        TranslationInputData in = new TranslationInputData(message);
        return translationInteractor.execute(in);
    }

    public void openOptionsMenu(){
        JFrame f = new JFrame("Options");
        JPanel p = new JPanel();
        JButton b = initHighContrastButton();
        p.setLayout(new GridBagLayout());
        p.setBackground(MainView.messagesColor);
        p.add(b);
        f.setVisible(true);
        f.add(p);
        f.pack();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setSize(new Dimension(400, 300));
        f.setResizable(false);
    }

    private JButton initHighContrastButton() {
        JButton b = new JButton("Toggle High Contrast UI");
        Font highContrastUIButtonFont = new Font("Helvetica", Font.BOLD,24);
        b.setFont(highContrastUIButtonFont);
        b.addActionListener(e -> {
            System.out.println("Toggle High Contrast Button");
            int val = optionsInteractor.execute(optionsInteractor.TOGGLE_HIGH_CONTRAST);
            if(val == optionsInteractor.INVALID_OPTION){
                System.out.println("MainController -> optionsInteractor -> invalid action");
            } else{
                boolean highContrast = val == 1;
                System.out.println("Main Controller HC " + highContrast);
                //TODO remove options feature complete
            }
        });
        return b;
    }

}
