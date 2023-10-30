package client.view;

import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainView extends JPanel implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "iKunnect - Main";

    JPanel basePanel = new JPanel();

    JPanel topPanel = new JPanel();
    JPanel options = new JPanel();
    JPanel channels = new JPanel();
    JPanel messages = new JPanel();

    Border mainBorder;

    MainViewModel mainViewModel = new MainViewModel();

    public MainView(MainController controller, MainViewModel viewModel) {
        initComponents();

    }

    public void initComponents() {
        //Font titleFont = new Font("Helvetica", Font.BOLD, 24);


        //setBorder(new TitledBorder(new LineBorder(new Color(0x111111)), MainViewModel.TITLE_LABEL, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, titleFont, new Color(0x111111)));

        setEnabled(true);
        setLayout(new GridBagLayout());
        mainBorder = new LineBorder(Color.black);

        basePanel.setLayout(new GridBagLayout());
        basePanel.setBorder(mainBorder);

        //Color topPanelColor = Color.decode("#595959");

        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setBorder(mainBorder);
        //topPanel.setBackground(topPanelColor);
        topPanel.setVisible(true);

        options.setPreferredSize(new Dimension(50, 680));
        options.setBorder(mainBorder);
        options.setVisible(true);
        //options.setBackground(new Color(217, 217, 217));

        channels.setPreferredSize(new Dimension(350, 680));
        channels.setBorder(mainBorder);
        channels.setVisible(true);
        //channels.setBackground(new Color(217, 217, 217));

        messages.setPreferredSize(new Dimension(700, 680));
        messages.setBorder(mainBorder);
        messages.setVisible(true);
        //messages.setBackground(new Color(217, 217, 217));

        basePanel.add(topPanel, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(options, new GridBagConstraints(0, 1, 1, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(channels, new GridBagConstraints(1, 1, 1, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.add(messages, new GridBagConstraints(2, 1, 2, 2, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        basePanel.setVisible(true);

        add(basePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    }

    public static void main(String[] args) {
        SmallJFrame frame = new SmallJFrame("Main");
        frame.add(new MainView(new MainController(), new MainViewModel()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.prepare();
        frame.setSize(new Dimension(1200, 800));
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
