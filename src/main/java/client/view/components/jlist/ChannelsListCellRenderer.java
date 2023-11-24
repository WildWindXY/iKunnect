package client.view.components.jlist;

import client.view.components.image.ImageFittingComponent;
import client.view.components.popupMenu.ChannelPopup;
import client.view.components.popupMenu.UserIconPopup;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ChannelsListCellRenderer extends DefaultListCellRenderer {
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
        panel.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        panel.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

        // Return the panel as the renderer component for the cell
        return panel;

    }
}