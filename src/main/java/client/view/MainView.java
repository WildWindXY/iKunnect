package client.view;

import client.data_access.ServerDataAccessObject;
import client.data_access.send_message.SendMessageDataAccess;
import client.interface_adapter.Login.LoginController;
import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.SendMessage.SendMessagePresenter;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInteractor;
import client.view.components.frames.SmallJFrame;
import client.view.components.jlist.ChannelsListCellRenderer;
import client.view.components.panels.PlainTextMessage;
import client.view.components.popupMenu.ChannelPopup;
import utils.InputUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static utils.MessageEncryptionUtils.initKey;

public class MainView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "iKunnect - Main";

    //--------------------- Styles ---------------------

    //--------------------- Colors ---------------------
    Color topPanelColor = Color.decode("#D7EAFA");
    Color optionsColor = Color.decode("#2C343B");
    Color channelsColor = Color.decode("#F4F9FC");
    Color messagesColor = Color.decode("#F6F9FC");
    Color inputFieldColor = Color.decode("#FBFCFD");

    //--------------------- Fonts ---------------------
    Font channelsFont = new Font("Helvetica", Font.PLAIN, 20);
    Font channelLabelFont = new Font("Helvetica", Font.ITALIC, 24);

    Font titleFont = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);

    private final Font messagesFont = new Font("Helvetica", Font.PLAIN, 20);

    //--------------------- Border ---------------------
    Border mainBorder = new LineBorder(Color.decode("#A4C1DB"));

    //--------------------- Components ---------------------
    private final JPanel basePanel = new JPanel();
    //JPopupMenu popupMenu = new JPopupMenu();
    private final JPanel topPanel = new JPanel();

    private JLabel titleLabel = new JLabel();
    private final JPanel optionsPanel = new JPanel();
    private final JScrollPane channelsScrollPane = new JScrollPane();
    private JList<String> channels = new JList<>();
    private final JPanel chatPanel = new JPanel();
    private JLabel channelLabel = new JLabel();
    private final JScrollPane messagesScrollPane = new JScrollPane();
    private final JPanel messagesPanel = new JPanel();
    JPanel messageOptions = new JPanel();
    JPanel inputFieldWrapper = new JPanel(new BorderLayout(0, 0));
    JPanel moreOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton moreOptionsButton;
    boolean inputFieldIsVisible = true;
    JScrollPane inputFieldScrollPane = new JScrollPane();
    JTextArea inputField = new JTextArea();

    private final JButton moreOptions1 = new JButton("Button 1");
    private final JButton moreOptions2 = new JButton("Button 2");
    private final JButton moreOptions3 = new JButton("Button 3");

    private final MainController mainController;
    private final MainViewModel mainViewModel;

    public MainView(MainController controller, MainViewModel viewModel) {
        this.mainController = controller;
        this.mainViewModel = viewModel;
        this.mainViewModel.addPropertyChangeListener(this);
        initComponents();
    }

    public void initComponents() {
        setEnabled(true);
        setLayout(new GridBagLayout());
        createBasePanel();

        setTitleLabel();
        initTopPanel();

        initPanel(optionsPanel, 50, 680, optionsColor);
        initChannelsScrollPane();

        addTestChannels(channelsFont, channelsColor);

        initPanel(chatPanel, 700, 680, messagesColor);
        setCurrentChannelLabel();
        addChannelSelectListener();
        addChannelRightClickListener();

        initMessagesPanel();
        addMessageSpacer();
        addTestMessages();

        initMessagesScrollPane();
        initMoreOptionsButton();
        addToInputFieldWrapper(inputFieldScrollPane);

        initInputField();
        addInputFieldListener();
        initInputFieldScrollPane();
        initMoreOptionsPanel();

        initChatPanel();
        addToBasePanel();
        addBaseToFrame();
    }

    private void addToInputFieldWrapper(JComponent component) {
        inputFieldWrapper.add(component);
    }

    private void initMoreOptionsPanel() {
        moreOptionsPanel.setBackground(messagesColor);
        moreOptionsPanel.add(moreOptions1);
        moreOptionsPanel.add(moreOptions2);
        moreOptionsPanel.add(moreOptions3);
    }

    private void addBaseToFrame() {
        add(basePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }

    private void setTitleLabel() {
        titleLabel = new JLabel("iKunnect");
        titleLabel.setFont(titleFont);
        titleLabel.setPreferredSize(new Dimension(300, 37));
    }

    private void addToBasePanel() {
        basePanel.add(topPanel, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(optionsPanel, new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(channelsScrollPane, new GridBagConstraints(1, 1, 1, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(chatPanel, new GridBagConstraints(2, 1, 2, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.setVisible(true);
    }

    private void initChatPanel() {
        chatPanel.add(messagesScrollPane);
        chatPanel.add(messageOptions);
        chatPanel.add(inputFieldWrapper);
        //chatPanel.add(inputFieldScrollPane);
    }

    public void showMoreOptions() {
        //Component[] components = inputFieldWrapper.getComponents();
        //inputFieldWrapper.remove(components[0]);
        //inputFieldWrapper.revalidate();
        //inputFieldWrapper.repaint();
        //inputFieldWrapper = new JPanel(new BorderLayout(0,0));
        inputFieldWrapper.remove(0);
        if (!inputFieldIsVisible) {
            addToInputFieldWrapper(inputFieldScrollPane);
        } else {
            addToInputFieldWrapper(moreOptionsPanel);
        }
        inputFieldWrapper.revalidate();
        inputFieldWrapper.repaint();
        inputFieldIsVisible = !inputFieldIsVisible;
    }

    private void addMoreOptionsButtonListener() {
        moreOptionsButton.addActionListener(l -> {
            showMoreOptions();
        });
    }

    private void addChannelSelectListener() {
        channels.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String value = channels.getSelectedValue();
                System.out.println(value);
                //Re-render messages
                channelLabel.setText(value);
                //TODO implement load messages from channel
            }
        });
    }

    private void addChannelRightClickListener() {

        channels.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int index = channels.locationToIndex(e.getPoint());
                    channels.setSelectedIndex(index);
                    ChannelPopup channelPopup = new ChannelPopup();
                    channelPopup.show(channels, e.getX(), e.getY());
                }
            }
        });
    }

    private void addInputFieldListener() {
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    String content = InputUtils.cleanInput(inputField.getText());
                    if (content.isEmpty()) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {
                        System.out.println("Send message with content, channel name, etc.");
                        System.out.println(content);
                        inputField.setText(null);
                        PlainTextMessage m = new PlainTextMessage(content);
                        messagesPanel.add(m);
                        messagesPanel.revalidate();
                        SwingUtilities.invokeLater(() -> {
                            JScrollBar verticalScrollBar = messagesScrollPane.getVerticalScrollBar();
                            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                        });

                    }
                }
            }
        });
    }

    private void initInputFieldScrollPane() {
        inputFieldScrollPane.setViewportView(inputField);
        inputFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputFieldScrollPane.getVerticalScrollBar().setBackground(messagesColor);
        inputFieldScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set vertical scrolling speed
        inputFieldScrollPane.setLayout(new ScrollPaneLayout());
        inputFieldScrollPane.setPreferredSize(new Dimension(720, 125));
    }

    private void initInputField() {
        inputField.setPreferredSize(new Dimension(720, 115));
        inputField.setBackground(inputFieldColor);
        inputField.setFont(messagesFont);
        inputField.setBorder(mainBorder);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
    }

    private void initMoreOptionsButton() {
        moreOptionsButton = new JButton("More Options");
        moreOptionsButton.setBackground(messagesColor);
        moreOptionsButton.setPreferredSize(new Dimension(30, 30));
        addMoreOptionsButtonListener();
        messageOptions.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageOptions.add(moreOptionsButton);
        messageOptions.setPreferredSize(new Dimension(720, 40));
        messageOptions.setBackground(messagesColor);
    }

    private void initMessagesScrollPane() {
        messagesScrollPane.setViewportView(messagesPanel);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set vertical scrolling speed
        messagesScrollPane.getVerticalScrollBar().setBackground(messagesColor);
        messagesScrollPane.setLayout(new ScrollPaneLayout());
        messagesScrollPane.setPreferredSize(new Dimension(720, 450));
    }

    private void addTestMessages() {
        String[] listOfMessages = new String[10];

        for (int i = 0; i < 10; ) {
            listOfMessages[i] = "Message " + ++i;
        }

        String catIpsum = "Cat ipsum dolor sit amet, i just saw other cats inside the house and nobody ask me before using my litter box yet behind the couch yet meow for food, then when human fills food dish, take a few bites of food and continue meowing. Have a lot of grump in yourself because you can't forget to be grumpy and not be like king grumpy cat purr when being pet and human clearly uses close to one life a night no one naps that long so i revive by standing on chestawaken! or has closed eyes but still sees you, yet nap all day purr like a car engine oh yes, there is my human slave woman she does best pats ever that all i like about her hiss meow , or stuff and things. Curl into a furry donut meow for food, then when human fills food dish, take a few bites of food and continue meowing so sleep over your phone and make cute snoring noises yet lasers are tiny mice stare at imaginary bug crusty butthole but scratch at door to be let outside, get let out then scratch at door immmediately after to be let back in. Poop in the plant pot scratch at fleas, meow until belly rubs, hide behind curtain when vacuum cleaner is on scratch strangers and poo on owners food i will ruin the couch with my claws so bleghbleghvomit my furball really tie the room together, so prow?? ew dog you drink from the toilet, yum yum warm milk hotter pls, ouch too hot, for human is in bath tub, emergency! drowning! meooowww! and pretend you want to go out but then don't. Warm up laptop with butt lick butt fart rainbows until owner yells pee in litter box hiss at cats i dreamt about fish yum! for dismember a mouse and then regurgitate parts of it on the family room floor for sit by the fire and meowwww. Sniff all the things is good you understand your place in my world naughty running cat intently sniff hand tuxedo cats always looking dapper. ";
        listOfMessages[0] = catIpsum;
        int i = 0;
        for (String listOfMessage : listOfMessages) {
            messagesPanel.add(new PlainTextMessage("cxk", listOfMessage, i++ % 2));
        }
    }

    private void initMessagesPanel() {
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(messagesColor);
    }

    private void addMessageSpacer() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(700, 20));
        p.setBackground(messagesColor);
        messagesPanel.add(p);
    }

    private void setCurrentChannelLabel() {
        channelLabel = new JLabel("Current Channel Name");
        channelLabel.setFont(channelLabelFont);
        JPanel channelLabelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        channelLabelWrapper.add(channelLabel);
        channelLabelWrapper.setPreferredSize(new Dimension(700, 40));
        channelLabelWrapper.setBackground(messagesColor);
        chatPanel.add(channelLabelWrapper);
    }

    private void addTestChannels(Font channelsFont, Color channelsColor) {
        String[] listOfChannels = new String[20];

        for (int i = 0; i < 20; ) {
            listOfChannels[i] = "Channel " + ++i;
        }

        channels = new JList<>(listOfChannels);
        channels.setCellRenderer(new ChannelsListCellRenderer(50)); // Set the height of each cell to 20 pixels
        channels.setFont(channelsFont);
        channels.setBackground(channelsColor);
        channels.setLayoutOrientation(JList.VERTICAL);
        channels.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        channelsScrollPane.setViewportView(channels);
        channelsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        channelsScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set vertical scrolling speed
        channelsScrollPane.getVerticalScrollBar().setBackground(channelsColor);
    }

    private void initChannelsScrollPane() {
        channelsScrollPane.setPreferredSize(new Dimension(350, 680));
        channelsScrollPane.setBorder(mainBorder);
        channelsScrollPane.setBackground(channelsColor);
        channelsScrollPane.setLayout(new ScrollPaneLayout());
    }

    private void initPanel(JPanel options, int width, int height, Color optionsColor) {
        options.setPreferredSize(new Dimension(width, height));
        options.setBorder(mainBorder);
        options.setBackground(optionsColor);
    }

    private void initTopPanel() {
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.setPreferredSize(new Dimension(1000, 62));
        topPanel.setBorder(mainBorder);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topPanel.setBackground(topPanelColor);
    }

    private void createBasePanel() {
        basePanel.setLayout(new GridBagLayout());
        basePanel.setBorder(mainBorder);
    }

    public static void main(String[] args) {
        SmallJFrame frame = new SmallJFrame("Main");
        try {
            initKey("1111222233334444");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MainViewModel mainViewModel = new MainViewModel();
        SendMessagePresenter sendMessagePresenter = new SendMessagePresenter(mainViewModel);
        ServerDataAccessObject serverDAO = new ServerDataAccessObject("localhost", 8964);
        SendMessageDataAccessInterface sendMessageDataAccess = new SendMessageDataAccess(serverDAO);
        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccess, sendMessagePresenter);

        frame.add(new MainView(new MainController("CAIXUKUN", sendMessageInteractor), mainViewModel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.prepare();
        frame.setSize(new Dimension(1200, 800));
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
