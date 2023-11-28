package client.view;

import client.data_access.high_contrast.HighContrastState;
import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.SendMessage.SendMessageState;
import client.use_case.HighContrast.HighContrastOutputData;
import client.view.components.image.ImageFittingComponent;
import client.view.components.panels.MessagesJPanel;
import utils.InputUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "Main Window";

    //--------------------- Styles ---------------------

    //--------------------- Colors ---------------------
    public static final Color topPanelColor = Color.decode("#D7EAFA");
    public static final Color scrollBarThumbColor = Color.decode("#EDF7FF");
    public static final Color optionsColor = Color.decode("#2C343B");
    public static final Color optionsHoverColor = Color.decode("#3f4b55");
    public static final Color optionsClickColor = Color.decode("#5a6a78");
    public static final Color channelsColor = Color.decode("#F4F9FC");
    public static final Color messagesColor = Color.decode("#F6F9FC");
    public static final Color inputFieldColor = Color.decode("#FBFCFD");
    public static final Color moreOptionsColor = messagesColor;
    public static final Color moreOptionsHoverColor = Color.decode("#D4EDFF");
    public static final Color moreOptionsClickColor = Color.decode("#F8FCFF");
    public static final Color mainBorderColor = Color.decode("#A4C1DB");
    public static final Color scrollBarBorderColor = Color.decode("#CAE5FC");
    public static final Color channelsSelectedColor = Color.decode("#E8F6FF");


    //--------------------- High Contrast Colors ---------------------
    public static final Color topPanelColorHC = Color.decode("#00000");

    public static final Color scrollBarThumbColorHC = Color.decode("#FFFFFF");
    public static final Color optionsColorHC = Color.decode("#000000");
    public static final Color optionsHoverColorHC = Color.decode("#00FFFF");
    public static final Color optionsClickColorHC = Color.decode("#FFFFFF");
    public static final Color channelsColorHC = Color.decode("#000000");
    public static final Color messagesColorHC = Color.decode("#000000");
    public static final Color inputFieldColorHC = Color.decode("#00000");
    public static final Color HCTextColor = Color.decode("#FFFFFF");
    public static final Color HCLeftMessageColor = Color.decode("#000080");
    public static final Color HCRightMessageColor = Color.decode("#000000");

    public static final Color moreOptionsColorHC = optionsColorHC;
    public static final Color moreOptionsHoverColorHC = optionsHoverColorHC;
    public static final Color moreOptionsClickColorHC = optionsClickColorHC;
    public static final Color mainBorderHCColor = Color.decode("#FFFFFF");
    public static final Color channelsSelectedColorHC = Color.decode("#FFFFFF");


    //--------------------- Fonts ---------------------
    public static final Font channelsFont = new Font("Helvetica", Font.PLAIN, 20);
    public static final Font channelLabelFont = new Font("Helvetica", Font.ITALIC, 24);

    public static final Font titleFont = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);

    public static final Font messagesFont = new Font("Helvetica", Font.PLAIN, 20);
    public static final Font channelsFontHC = new Font("Helvetica", Font.PLAIN, 20);
    public static final Font channelLabelFontHC = new Font("Helvetica", Font.ITALIC, 24);
    public static final Font titleFontHC = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);

    public static final Font messagesFontHC = new Font("Helvetica", Font.PLAIN, 20);

    public static final Font jMenuItemFont = new Font("Helvetica", Font.PLAIN, 20);


    //--------------------- Border ---------------------
    public static final Border mainBorder = new LineBorder(mainBorderColor);
    public static final Border mainBorderHC = new LineBorder(mainBorderHCColor);

    //--------------------- Components ---------------------
    private JPanel basePanel;
    private JPanel topPanel;
    private JLabel titleLabel;
    private JPanel optionsPanel;
    private JButton options;
    private JScrollPane channelsScrollPane;
    private JList<String> channels;
    private JPanel chatPanel;
    private JLabel channelLabel;
    private JScrollPane messagesScrollPane;
    private JPanel messagesPanel;
    private JPanel messageOptions;
    private JPanel inputFieldWrapper;
    private JPanel moreOptionsPanel;
    private JButton moreOptionsButton;
    private JScrollPane inputFieldScrollPane;
    private JTextArea inputField;

    private JButton moreOptions1 = new JButton("Button 1");
    private JButton moreOptions2 = new JButton("Button 2");
    private JButton moreOptions3 = new JButton("Button 3");

    //--------------------- CA Engine ---------------------

    private final MainController mainController;
    private final MainViewModel mainViewModel;
    private final HighContrastState highContrastState;

    //--------------------- Values ---------------------
    boolean inputFieldIsVisible = true;
    private boolean HC;
    private String inputFieldTemp = "";

    public MainView(MainController controller, MainViewModel viewModel, HighContrastOutputData outputData) {
        this.mainController = controller;
        this.mainViewModel = viewModel;
        this.mainViewModel.addPropertyChangeListener(this);
        this.highContrastState = new HighContrastState();
        highContrastState.setHighContrast(outputData.getHighContrast());
        UIManager.put("PopupMenu.background", new Color(0));
        UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());

        initComponents(highContrastState);
        Runnable receive = () -> {
            while (true) {
                mainController.getMessage();
            }
        };

        Thread thread = new Thread(receive);
        thread.start();

    }

    public void initComponents(HighContrastState highContrastState) {
        if (inputField != null) {
            inputFieldTemp = inputField.getText();
        }
        HC = highContrastState.getHighContrast();

        initAttributes();
        removeAll();
        setEnabled(true);
        setLayout(new GridBagLayout());
        createBasePanel();
        moreOptions3.addActionListener(e -> {
            basePanel.removeAll();
            basePanel.revalidate();
            basePanel.repaint();
        });

        setTitleLabel();
        initTopPanel();

        //initPanel(optionsPanel, 50, 680, optionsColor);
        initOptionsPanel();
        setOptionsPanelLayout();
        initOptionsButton();
        addOptionsButtonToPanel();

        initChannelsScrollPane();
        addTestChannels();
        initChatPanel();
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

        addToChatPanel();
        addToBasePanel();
        addBaseToFrame();

        basePanel.revalidate();
        basePanel.repaint();
    }

    private void initOptionsPanel() {
        optionsPanel.setPreferredSize(new Dimension(50, 680));
        //optionsPanel.setMaximumSize(new Dimension(50, Integer.MAX_VALUE));
        optionsPanel.setBorder(HC ? mainBorderHC : mainBorder);
        optionsPanel.setBackground(HC ? optionsColorHC : optionsColor);
    }

    private void initAttributes() {
        basePanel = new JPanel();
        topPanel = new JPanel();

        titleLabel = new JLabel();
        optionsPanel = new JPanel();
        channelsScrollPane = new JScrollPane();
        channels = new JList<>();
        chatPanel = new JPanel();
        channelLabel = new JLabel();
        messagesScrollPane = new JScrollPane();
        messagesPanel = new JPanel();
        messageOptions = new JPanel();
        inputFieldWrapper = new JPanel(new BorderLayout(0, 0));
        moreOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputFieldIsVisible = true;
        inputFieldScrollPane = new JScrollPane();
        inputField = new JTextArea(inputFieldTemp);

        moreOptions1 = new JButton("Button 1");
        moreOptions2 = new JButton("Button 2");
        moreOptions3 = new JButton("Button 3");
    }

    private void setOptionsPanelLayout() {
        optionsPanel.setLayout(new BorderLayout());
    }

    private void initOptionsButton() {
        options = new JButton(new ImageIcon(new ImageIcon(HC ? "src/main/resources/hamburgerHC.png" : "src/main/resources/hamburger.png").getImage().getScaledInstance(50, 50, 4)));
        options.setUI(new BasicButtonUI() {
            @Override
            public void paintButtonPressed(Graphics g, AbstractButton b) {

            }
        });
        options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                options.setBackground(HC ? optionsHoverColorHC : optionsHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                options.setBackground(HC ? optionsColorHC : optionsColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                options.setBackground(HC ? optionsClickColorHC : optionsClickColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (options.contains(e.getPoint())) {
                    options.setBackground(HC ? optionsHoverColorHC : optionsHoverColor);
                }
            }

        });

        options.setPreferredSize(new Dimension(75, 75));
        options.setBorderPainted(false);
        options.setBackground(HC ? optionsColorHC : optionsColor);
        options.addActionListener(e -> {
            System.out.println("Options Button");
            mainController.openOptionsMenu();
        });
    }

    private void addOptionsButtonToPanel() {
        optionsPanel.add(options, BorderLayout.SOUTH);
    }

    private void addToInputFieldWrapper(JComponent component) {
        inputFieldWrapper.add(component);
    }

    private void initMoreOptionsPanel() {
        moreOptionsPanel.setBackground(HC ? messagesColorHC : messagesColor);
        moreOptionsPanel.add(moreOptions1);
        moreOptionsPanel.add(moreOptions2);
        moreOptionsPanel.add(moreOptions3);
    }

    private void addBaseToFrame() {
        add(basePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }

    private void setTitleLabel() {
        titleLabel = new JLabel("iKunnect");
        titleLabel.setFont(HC ? titleFontHC : titleFont);
        if (HC) {
            titleLabel.setForeground(HCTextColor);
        }
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
        chatPanel.setPreferredSize(new Dimension(700, 680));
        chatPanel.setBorder(HC ? mainBorderHC : mainBorder);
        chatPanel.setBackground(HC ? messagesColorHC : messagesColor);
    }

    private void addToChatPanel() {
        chatPanel.add(messagesScrollPane);
        chatPanel.add(messageOptions);
        chatPanel.add(inputFieldWrapper);
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
                    ChannelPopup channelPopup = new ChannelPopup(HC);
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
                        mainController.sendMessage(content, channelLabel.getText());


                    }
                }
            }
        });
    }

    private void initInputFieldScrollPane() {
        inputFieldScrollPane.setViewportView(inputField);
        inputFieldScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar verticalScrollBar = inputFieldScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                trackColor = HC ? inputFieldColorHC : inputFieldColor;
                thumbColor = HC ? scrollBarThumbColorHC : scrollBarThumbColor;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the border color
                g2.setColor(HC ? null : scrollBarBorderColor);
                g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);

                // Set the inside color
                g2.setColor(HC ? scrollBarThumbColorHC : scrollBarThumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.width - 2, thumbBounds.height - 2, 5, 5);

                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
        });
        inputFieldScrollPane.setLayout(new ScrollPaneLayout());
        inputFieldScrollPane.setPreferredSize(new Dimension(720, 125));
    }

    private void initInputField() {
        inputField.setPreferredSize(new Dimension(720, 115));
        inputField.setBackground(HC ? inputFieldColorHC : inputFieldColor);
        inputField.setFont(HC ? messagesFontHC : messagesFont);
        if (HC) {
            inputField.setForeground(HCTextColor);
        }
        inputField.setBorder(HC ? mainBorderHC : mainBorder);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        inputField.setSelectionColor(HC ? Color.WHITE : Color.decode("#CCEBFF"));
        inputField.setSelectedTextColor(Color.BLACK);
    }

    private void initMoreOptionsButton() {
        moreOptionsButton = new JButton("+");
        moreOptionsButton.setFont(new Font("Helvetica", Font.PLAIN, 24));
        moreOptionsButton.setForeground(HC ? Color.decode("#DCDCDC") : null);
        moreOptionsButton.setBackground(HC ? messagesColorHC : messagesColor);
        moreOptionsButton.setPreferredSize(new Dimension(30, 30));
        addMoreOptionsButtonListener();
        messageOptions.setLayout(new FlowLayout(FlowLayout.LEFT));
        messageOptions.add(moreOptionsButton);
        messageOptions.setPreferredSize(new Dimension(720, 40));
        messageOptions.setBackground(HC ? messagesColorHC : messagesColor);
        moreOptionsButton.setBorder(HC ? mainBorderHC : mainBorder);
        moreOptionsButton.setUI(new BasicButtonUI() {
            @Override
            public void paintButtonPressed(Graphics g, AbstractButton b) {
            }
        });
        moreOptionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                moreOptionsButton.setBackground(HC ? moreOptionsHoverColorHC : moreOptionsHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moreOptionsButton.setBackground(HC ? moreOptionsColorHC : moreOptionsColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                moreOptionsButton.setBackground(HC ? moreOptionsClickColorHC : moreOptionsClickColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (moreOptionsButton.contains(e.getPoint())) {
                    moreOptionsButton.setBackground(HC ? moreOptionsHoverColorHC : moreOptionsHoverColor);
                }
            }

        });
    }

    private void initMessagesScrollPane() {
        messagesScrollPane.setViewportView(messagesPanel);
        messagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagesScrollPane.setLayout(new ScrollPaneLayout());
        messagesScrollPane.setPreferredSize(new Dimension(720, 450));
        JScrollBar verticalScrollBar = messagesScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16); // Set vertical scrolling speed
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                trackColor = HC ? messagesColorHC : messagesColor;
                thumbColor = HC ? scrollBarThumbColorHC : scrollBarThumbColor;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the border color
                g2.setColor(HC ? null : scrollBarBorderColor);
                g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);

                // Set the inside color
                g2.setColor(HC ? scrollBarThumbColorHC : scrollBarThumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.width - 2, thumbBounds.height - 2, 5, 5);

                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
        });
    }

    private JButton createZeroButton() {
        JButton button = new JButton("zero button");
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
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
            messagesPanel.add(new PlainTextMessage("cxk", listOfMessage, 0, i++ % 2));
        }
        messagesPanel.add(new PlainTextMessage("cxk", "测试插入", 0, 0), 2);
        messagesPanel.add(new PlainTextMessage("You", "测试插入", 0, 1), 2);
    }

    private void initMessagesPanel() {
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(HC ? messagesColorHC : messagesColor);
    }

    private void addMessageSpacer() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(700, 20));
        p.setBackground(HC ? messagesColorHC : messagesColor);
        messagesPanel.add(p);
    }

    private void setCurrentChannelLabel() {
        channelLabel = new JLabel("Current Channel Name");
        channelLabel.setFont(HC ? channelLabelFont : channelLabelFontHC);
        if (HC) {
            channelLabel.setForeground(HCTextColor);
        }
        JPanel channelLabelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        channelLabelWrapper.add(channelLabel);
        channelLabelWrapper.setPreferredSize(new Dimension(700, 40));
        channelLabelWrapper.setBackground(HC ? messagesColorHC : messagesColor);
        chatPanel.add(channelLabelWrapper);
    }

    private void addTestChannels() {
        String[] listOfChannels = new String[20];

        for (int i = 0; i < 20; ) {
            listOfChannels[i] = "Channel " + ++i;
        }

        channels = new JList<>(listOfChannels);
        channels.setCellRenderer(new ChannelsListCellRenderer(50)); // Set the height of each cell to 20 pixels
        channels.setFont(HC ? channelsFontHC : channelsFont);
        if (HC) {
            channels.setForeground(HCTextColor);
        }
        channels.setBackground(HC ? channelsColorHC : channelsColor);
        channels.setLayoutOrientation(JList.VERTICAL);
        channels.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        channelsScrollPane.setViewportView(channels);

    }

    private void initChannelsScrollPane() {
        channelsScrollPane.setPreferredSize(new Dimension(350, 680));
        channelsScrollPane.setBorder(HC ? mainBorderHC : mainBorder);
        channelsScrollPane.setBackground(HC ? channelsColorHC : channelsColor);
        channelsScrollPane.setLayout(new ScrollPaneLayout());
        JScrollBar verticalScrollBar = channelsScrollPane.getVerticalScrollBar();

        channelsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalScrollBar.setUnitIncrement(16); // Set vertical scrolling speed
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                trackColor = HC ? channelsColorHC : channelsColor;
                thumbColor = HC ? scrollBarThumbColorHC : scrollBarThumbColor;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the border color
                g2.setColor(HC ? null : scrollBarBorderColor);
                g2.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width - 1, thumbBounds.height - 1, 5, 5);

                // Set the inside color
                g2.setColor(HC ? scrollBarThumbColorHC : scrollBarThumbColor);
                g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.width - 2, thumbBounds.height - 2, 5, 5);

                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
        });
    }

    private void initTopPanel() {
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.setMinimumSize(new Dimension(600, 70));
        //topPanel.setPreferredSize(new Dimension(1000, 70));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topPanel.setBorder(HC ? mainBorderHC : mainBorder);
        topPanel.setBackground(HC ? topPanelColorHC : topPanelColor);

    }

    private void createBasePanel() {
        basePanel.setLayout(new GridBagLayout());
        basePanel.setBorder(HC ? mainBorderHC : mainBorder);
    }

//    public static void main(String[] args) {
//        SmallJFrame frame = new SmallJFrame("Main");
//        try {
//            initKey("1111222233334444");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        MainViewModel mainViewModel = new MainViewModel();
//        SendMessagePresenter sendMessagePresenter = new SendMessagePresenter(mainViewModel);
//        ReceiveMessagePresenter receiveMessagePresenter = new ReceiveMessagePresenter(mainViewModel);
//        ServerDataAccessObject serverDAO = new ServerDataAccessObject("localhost", 8964);
//        SendMessageDataAccessInterface sendMessageDataAccess = new SendMessageDataAccess(serverDAO);
//        ReceiveMessageDataAccessInterface receiveMessageDataAccess = new ReceiveMessageDataAccess(serverDAO);
//        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccess, sendMessagePresenter);
//        ReceiveMessageInteractor receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccess, receiveMessagePresenter);
//
//        TranslateDataAccessInterface translationDataAccessObject = new TranslateDataAccess();
//        TranslationInputBoundary translationInteractor = new TranslationInteractor(translationDataAccessObject);
//
//        frame.add(new MainView(new MainController("CAIXUKUN", sendMessageInteractor, receiveMessageInteractor, translationInteractor), mainViewModel));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.prepare();
//        frame.setSize(new Dimension(1200, 800));
//        frame.setLocationRelativeTo(null);
//        frame.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//                    frame.dispose();
//                }
//            }
//        });
//    }

    class PlainTextMessage extends MessagesJPanel {

        JPanel userIconWrapper;
        String username;
        JComponent userIcon;
        JPanel labelWrapper = new JPanel();
        JLabel usernameLabel;
        JTextArea messageContentText;

        JPanel contentWrapper;

        public PlainTextMessage(String username, String content, long timestamp, int orientation) {

            super();
            this.username = username;
            boolean isLeft;

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");

            Date date = new Date(timestamp);

            String formattedDate = dateFormat.format(date);

            if (orientation != MessagesJPanel.LEFT && orientation != MessagesJPanel.RIGHT) {
                isLeft = true;
                System.out.println("Invalid component orientation.");
            } else {
                isLeft = orientation == MessagesJPanel.LEFT;
            }

            //User Icon
            userIcon = renderUserIcon(username);
            userIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        System.out.println("right click");
                        UserIconPopup iconPopup = new UserIconPopup(HC);
                        iconPopup.show(userIcon, e.getX(), e.getY());
                    }
                }
            });
            userIconWrapper = new JPanel();
            userIconWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
            userIconWrapper.add(userIcon);
            userIconWrapper.setBackground(HC ? messagesColorHC : messagesColor);

            //Username Label
            if (isLeft) {
                username = username + " " + formattedDate + " ";
                usernameLabel = new JLabel(username);
                labelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
                labelWrapper.setBackground(HC ? leftColorHC : leftColor);
            } else {
                username = formattedDate + " You" + " ";
                usernameLabel = new JLabel(username);
                labelWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
                labelWrapper.setBackground(HC ? rightColorHC : rightColor);
            }
            int WIDTH = Math.min(Math.max(getTextWidth(content), getTextWidth(username)), 450);

            labelWrapper.add(usernameLabel);
            labelWrapper.setPreferredSize(new Dimension(WIDTH, 25));
            labelWrapper.setBorder(HC ? mainBorderHC : mainBorder);
            usernameLabel.setFont(labelFont);


            //Text
            messageContentText = new JTextArea(content);

            messageContentText.setSelectionColor(HC ? Color.BLACK : Color.decode("#CCEBFF"));
            messageContentText.setSelectedTextColor(HC ? Color.WHITE : Color.BLACK);
            messageContentText.setEditable(false);

            messageContentText.setLineWrap(true);
            messageContentText.setWrapStyleWord(true);
            messageContentText.setBorder(mainBorder);
            messageContentText.setFont(messagesFont);
            if (!isLeft) {
                messageContentText.setBackground(HC ? rightColorHC : rightColor);
            } else {
                messageContentText.setBackground(HC ? leftColorHC : leftColor);
            }
            messageContentText.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        System.out.println("right click");
                        if (messageContentText.getSelectionStart() != messageContentText.getSelectionEnd()) {
                            String selection = messageContentText.getSelectedText();
                            System.out.println(selection);
                            PlainTextMessagePopup messagePopup = new PlainTextMessagePopup(true, selection);
                            messagePopup.show(messageContentText, e.getX(), e.getY());
                        } else {
                            PlainTextMessagePopup messagePopup = new PlainTextMessagePopup(false, messageContentText.getText());
                            messagePopup.show(messageContentText, e.getX(), e.getY());
                        }
                    }
                }
            });

            //Text Padding
            messageContentText.setBorder(new EmptyBorder(10, 10, 10, 10));
            //Text Wrapper
            JPanel textWrapper = new JPanel(new BorderLayout());
            textWrapper.setBorder(HC ? mainBorderHC : mainBorder);
            textWrapper.add(messageContentText);

            //Setting height
            int prefHeight = getHeight(content);
            messageContentText.setPreferredSize(new Dimension(WIDTH, prefHeight));
            userIconWrapper.setPreferredSize(new Dimension(userIconWrapper.getPreferredSize().width, prefHeight + 30));

            //Wrapping username label and text message together into a large panel
            contentWrapper = new JPanel();
            contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
            contentWrapper.add(labelWrapper);
            contentWrapper.add(textWrapper);
            Dimension d = messageContentText.getPreferredSize();
            contentWrapper.setPreferredSize(new Dimension(d.width, prefHeight + 70));

            if (isLeft) {
                setLayout(new FlowLayout(FlowLayout.LEFT));
                add(userIconWrapper);
                add(contentWrapper);
                messageContentText.setForeground(HC ? HCLeftMessageColor : null);
                usernameLabel.setForeground(HC ? HCLeftMessageColor : null);
            } else {
                setLayout(new FlowLayout(FlowLayout.RIGHT));
                add(contentWrapper);
                add(userIconWrapper);
                messageContentText.setForeground(HC ? HCRightMessageColor : null);
                usernameLabel.setForeground(HC ? HCRightMessageColor : null);
            }
            setBackground(HC ? messagesColorHC : messagesColor);
            setBorder(null);
        }

//        public PlainTextMessage(String content) {
//            this("self", content,0, MessagesJPanel.RIGHT);
//        }
//

        private int getHeight(String text) {
            if (text.contains("\n")) {
                String[] subs = text.split("\n");
                int sum = 0;
                for (String sub : subs) {
                    sum += getHeight(sub);
                }
                return sum;
            }
            FontRenderContext frc = new FontRenderContext(null, true, true);
            LineMetrics lineMetrics = messagesFont.getLineMetrics("Sample Text", frc);
            float rowHeight = lineMetrics.getHeight() + lineMetrics.getLeading();

            int prefHeight;
            int textLength = text.length();
            int numRows = textLength / 46 + 1;

            prefHeight = (int) (rowHeight * numRows);
            return prefHeight;
        }

        private int getTextWidth(String text) {

            // Create a temporary graphics object to obtain FontMetrics
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setFont(messagesFont);
            FontMetrics fontMetrics = g.getFontMetrics();

            // Calculate the width of the text in pixels

            if (text.contains("\n")) {
                String[] subs = text.split("\n");
                int max = 0;
                for (String sub : subs) {
                    max = Math.max(max, getTextWidth(sub));
                }
            }

            return fontMetrics.stringWidth(text) + 30;

        }

        private void updateContentSize() {
            int newHeight = getHeight(messageContentText.getText());
            messageContentText.setPreferredSize(new Dimension(messageContentText.getWidth(), newHeight));

            Dimension d = messageContentText.getPreferredSize();
            contentWrapper.setPreferredSize(new Dimension(d.width, newHeight + 70));

            userIconWrapper.setPreferredSize(new Dimension(userIconWrapper.getPreferredSize().width, newHeight + 30));

            contentWrapper.revalidate();
            contentWrapper.repaint();
        }

        private void addText(String content) {
            messageContentText.setText(messageContentText.getText() + "\n\n" + content);
            updateContentSize();
        }

        class PlainTextMessagePopup extends JPopupMenu {

            public PlainTextMessagePopup(boolean textSelected) {
                this(textSelected, "");
            }


            public PlainTextMessagePopup(boolean textSelected, String selectedText) {
                super();

                if (textSelected) {
                    if (selectedText.isEmpty()) {
                        System.out.println("No Text Selected");
                    }
                    JMenuItem copyText = new JMenuItem("Copy");

                    copyText.setFont(jMenuItemFont);
                    copyText.setUI(new CustomMenuItemUI());

                    copyText.addActionListener(e -> {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        StringSelection stringSelection = new StringSelection(selectedText);
                        clipboard.setContents(stringSelection, null);
                        System.out.println("Text copied to clipboard");
                    });
                    add(copyText);
                } else {
                    JMenuItem forward = new JMenuItem("Forward");
                    JMenuItem copyMessage = new JMenuItem("Copy Message");


                    copyMessage.addActionListener(e -> {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        StringSelection stringSelection = new StringSelection(selectedText);
                        clipboard.setContents(stringSelection, null);
                        System.out.println("Text copied to clipboard");
                    });
                    JMenuItem translate = new JMenuItem("Translate");
                    translate.addActionListener(e -> {
                        addText(mainController.translateMessage(selectedText));
                    });

                    forward.setFont(jMenuItemFont);
                    copyMessage.setFont(jMenuItemFont);
                    translate.setFont(jMenuItemFont);

                    forward.setUI(new CustomMenuItemUI());
                    copyMessage.setUI(new CustomMenuItemUI());
                    translate.setUI(new CustomMenuItemUI());

                    add(forward);
                    add(copyMessage);
                    add(translate);
                }
            }

        }

    }

    class ChannelPopup extends JPopupMenu {

        public ChannelPopup(boolean HC) {
            super();

            JMenuItem renameChannel = new JMenuItem("Rename Channel");
            JMenuItem hideChannel = new JMenuItem("Hide Channel");
            JMenuItem leaveChannel = new JMenuItem("Leave Channel");

            renameChannel.setFont(jMenuItemFont);
            hideChannel.setFont(jMenuItemFont);
            leaveChannel.setFont(jMenuItemFont);

            renameChannel.setUI(new CustomMenuItemUI());
            hideChannel.setUI(new CustomMenuItemUI());
            leaveChannel.setUI(new CustomMenuItemUI());

            add(renameChannel);
            add(hideChannel);
            add(leaveChannel);
        }
    }

    class UserIconPopup extends JPopupMenu {
        public UserIconPopup(boolean HC) {
            super();

            JMenuItem viewProfile = new JMenuItem("View Profile");
            JMenuItem sendMessage = new JMenuItem("Send Message");
            JMenuItem addFriend = new JMenuItem("Add Friend");

            viewProfile.setFont(jMenuItemFont);
            sendMessage.setFont(jMenuItemFont);
            addFriend.setFont(jMenuItemFont);
            setBackground(null);
            viewProfile.setUI(new CustomMenuItemUI());
            sendMessage.setUI(new CustomMenuItemUI());
            addFriend.setUI(new CustomMenuItemUI());

            add(viewProfile);
            add(sendMessage);
            add(addFriend);


        }
    }


    class CustomMenuItemUI extends BasicMenuItemUI {
        @Override
        protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
            ButtonModel model = menuItem.getModel();
            Color color = model.isArmed() ? (HC ? moreOptionsHoverColorHC : moreOptionsHoverColor) : (HC ? moreOptionsColorHC : moreOptionsColor); // Set your desired colors
            g.setColor(color);
            g.fillRect(0, 0, menuItem.getWidth(), menuItem.getHeight());
        }

        @Override
        protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
            Color textColor = HC ? Color.WHITE : Color.BLACK;
            Font originalFont = menuItem.getFont();

            // Set custom font (optional)
            Font customFont = new Font("Helvetica", Font.PLAIN, 20);
            menuItem.setFont(customFont);

            g.setColor(textColor);
            super.paintText(g, menuItem, textRect, text);

            // Reset the font to the original after painting
            menuItem.setFont(originalFont);
        }
    }

    class ChannelsListCellRenderer extends DefaultListCellRenderer {
        private final int height;
        private String username = null;

        public ChannelsListCellRenderer(int height) {
            this.height = height;
        }

        public ChannelsListCellRenderer(int height, String username) {
            this.height = height;
            this.username = username;
        }


        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Set the height of each cell
            label.setPreferredSize(new Dimension(255, height));

            // Add vertical spacing between cells (optional)
            label.setBorder(new EmptyBorder(5, 5, 5, 5));

            // Create a panel to hold multiple components inside each cell
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
            panel.setBorder(BorderFactory.createLineBorder(Color.decode("#E8F3FD")));

            //Load image from server using username/uuid???
            String imagePath = "src/main/resources/userIcon.jpg";
            //Default UserIcon
            JComponent imageFittingComponent = new JLabel("Image Failed To Load");
            try {
                imageFittingComponent = new ImageFittingComponent(imagePath);
            } catch (IOException ignored) {
            }

            imageFittingComponent.setPreferredSize(new Dimension(50, 50));

            // Add components to the panel
            panel.add(imageFittingComponent);
            panel.add(label);

            // Set the background and selection colors
            panel.setBackground(isSelected ? (HC ? channelsSelectedColorHC : channelsSelectedColor) : (HC ? channelsColorHC : channelsColor));
            label.setBackground(isSelected ? (HC ? channelsSelectedColorHC : channelsSelectedColor) : (HC ? channelsColorHC : channelsColor));
            label.setForeground(isSelected ? (HC ? Color.BLACK : null) : (HC ? HCTextColor : null));

            // Return the panel as the renderer component for the cell
            return panel;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String eventName = evt.getPropertyName();
        switch (eventName) {
            case "sendMessageState" -> {
                SendMessageState sendMessageState = (SendMessageState) evt.getNewValue();
                PlainTextMessage m = null;
                if (sendMessageState.getSender().isEmpty()) {
                    m = new PlainTextMessage("You", sendMessageState.getMessage(), sendMessageState.getTimestamp(), 1);
                } else {
                    m = new PlainTextMessage(sendMessageState.getSender(), sendMessageState.getMessage(), sendMessageState.getTimestamp(), 0);
                }
                messagesPanel.add(m);
                messagesPanel.revalidate();
                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalScrollBar = messagesScrollPane.getVerticalScrollBar();
                    verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                });
            }
            case "highContrastState" -> {
                HighContrastState highContrastState = (HighContrastState) evt.getNewValue();
                boolean hc = highContrastState.getHighContrast();
                System.out.println("MainView HC " + hc);
                initComponents(highContrastState);
            }
            case "addFriend" -> {
                //TODO Add Friend Success
            }

        }
    }
}
