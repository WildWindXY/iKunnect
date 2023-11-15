package client.view.components.panels;

import client.view.components.popupMenu.PlainTextMessagePopup;
import client.view.components.popupMenu.UserIconPopup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

public class PlainTextMessage extends MessagesJPanel {

    JPanel userIconWrapper;
    String username;
    JComponent userIcon;
    JPanel labelWrapper = new JPanel();
    JLabel usernameLabel;
    JTextArea contentTextArea;

    public PlainTextMessage(String username, String content, int orientation) {

        super();
        this.username = username;
        boolean isLeft;

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
                    UserIconPopup iconPopup = new UserIconPopup();
                    iconPopup.show(userIcon, e.getX(), e.getY());
                }
            }
        });
        userIconWrapper = new JPanel();
        userIconWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        userIconWrapper.add(userIcon);
        userIconWrapper.setBackground(leftColor);

        //Username Label
        if (isLeft) {
            usernameLabel = new JLabel(username);
            labelWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
            labelWrapper.setBackground(leftColor);
        } else {
            usernameLabel = new JLabel("You");
            labelWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
            labelWrapper.setBackground(rightColor);
        }
        int WIDTH = Math.min(getTextWidth(content), 450);

        labelWrapper.add(usernameLabel);
        labelWrapper.setPreferredSize(new Dimension(WIDTH, 30));
        labelWrapper.setBorder(mainBorder);
        usernameLabel.setFont(labelFont);

        //Text
        contentTextArea = new JTextArea(content);
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setBorder(mainBorder);
        contentTextArea.setFont(messagesFont);
        if (!isLeft) {
            contentTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            contentTextArea.setBackground(rightColor);
        } else {
            contentTextArea.setBackground(leftColor);
        }
        contentTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("right click");
                    if (contentTextArea.getSelectionStart() != contentTextArea.getSelectionEnd()) {
                        String selection = contentTextArea.getSelectedText();
                        System.out.println(selection);
                        PlainTextMessagePopup messagePopup = new PlainTextMessagePopup(true, selection);
                        messagePopup.show(contentTextArea, e.getX(), e.getY());
                    } else {
                        PlainTextMessagePopup messagePopup = new PlainTextMessagePopup(false);
                        messagePopup.show(contentTextArea, e.getX(), e.getY());
                    }
                }
            }
        });

        //Text Padding
        contentTextArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        //Text Wrapper
        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setBorder(mainBorder);
        textWrapper.add(contentTextArea);

        //Setting height
        int prefHeight = getHeight(content);
        contentTextArea.setPreferredSize(new Dimension(WIDTH, prefHeight));
        userIconWrapper.setPreferredSize(new Dimension(userIconWrapper.getPreferredSize().width, prefHeight + 30));

        //Wrapping username label and text message together into a large panel
        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.Y_AXIS));
        contentWrapper.add(labelWrapper);
        contentWrapper.add(textWrapper);
        Dimension d = contentTextArea.getPreferredSize();
        contentWrapper.setPreferredSize(new Dimension(d.width, prefHeight + 70));

        if (isLeft) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(userIconWrapper);
            add(contentWrapper);
        } else {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(contentWrapper);
            add(userIconWrapper);
        }
        setBackground(leftColor);
        setBorder(null);
    }

    public PlainTextMessage(String content) {
        this("self", content, MessagesJPanel.RIGHT);
    }


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

}
