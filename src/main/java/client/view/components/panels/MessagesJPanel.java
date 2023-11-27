package client.view.components.panels;

import client.view.components.image.ImageFittingComponent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.EventListener;

public abstract class MessagesJPanel extends JPanel {

    public final Color leftColor = Color.decode("#F6F9FC");
    public final Color rightColor = Color.decode("#F6FBF6");

    public final Color leftColorHC = Color.decode("#00FFFF");
    public final Color rightColorHC = Color.decode("#00FF00");
    public final Font labelFont = new Font("Helvetica", Font.ITALIC, 16);

    public final Font messagesFont = new Font("Helvetica", Font.PLAIN, 20);
    public final Border mainBorder = new LineBorder(Color.decode("#A4C1DB"));

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public MessagesJPanel() {
        super();
    }

    protected JComponent renderUserIcon(String username) {

        String imagePath = "src/main/resources/userIcon.jpg";
        //Default UserIcon
        JComponent imageFittingComponent = new JLabel("Image Failed To Load");
        try {
            imageFittingComponent = new ImageFittingComponent(imagePath);
        } catch (IOException ignored) {
        }

        imageFittingComponent.setPreferredSize(new Dimension(50, 50));
        return imageFittingComponent;
    }


}
