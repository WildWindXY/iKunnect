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
    public final Font labelFont = new Font("Helvetica", Font.ITALIC, 20);

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

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JScrollPane s = new JScrollPane();
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new PlainTextMessage("cxk", "short message qwertyuiop", MessagesJPanel.RIGHT));
        p.add(new PlainTextMessage("cxk", "short message\ntwo rows", MessagesJPanel.LEFT));
        p.add(new PlainTextMessage("cxk", "Cat ipsum dolor sit amet, i just saw other cats inside the house and nobody ask me before using my li", MessagesJPanel.LEFT));
        p.add(new PlainTextMessage("cxk", "Cat ipsum dolor sit amet, i just saw other cats inside the house and nobody ask me before using my litter box yet behind the couch yet meow for food, then when human fills food dish, take a few bites of food and continue meowing. Have a lot of grump in yourself because you can't forget to be grumpy and not be like", MessagesJPanel.LEFT));
        String catIpsum = "Cat ipsum dolor sit amet, i just saw other cats inside the house and nobody ask me before using my litter box yet behind the couch yet meow for food, then when human fills food dish, take a few bites of food and continue meowing. Have a lot of grump in yourself because you can't forget to be grumpy and not be like king grumpy cat purr when being pet and human clearly uses close to one life a night no one naps that long so i revive by standing on chestawaken! or has closed eyes but still sees you, yet nap all day purr like a car engine oh yes, there is my human slave woman she does best pats ever that all i like about her hiss meow , or stuff and things. Curl into a furry donut meow for food, then when human fills food dish, take a few bites of food and continue meowing so sleep over your phone and make cute snoring noises yet lasers are tiny mice stare at imaginary bug crusty butthole but scratch at door to be let outside, get let out then scratch at door immmediately after to be let back in. Poop in the plant pot scratch at fleas, meow until belly rubs, hide behind curtain when vacuum cleaner is on scratch strangers and poo on owners food i will ruin the couch with my claws so bleghbleghvomit my furball really tie the room together, so prow?? ew dog you drink from the toilet, yum yum warm milk hotter pls, ouch too hot, for human is in bath tub, emergency! drowning! meooowww! and pretend you want to go out but then don't. Warm up laptop with butt lick butt fart rainbows until owner yells pee in litter box hiss at cats i dreamt about fish yum! for dismember a mouse and then regurgitate parts of it on the family room floor for sit by the fire and meowwww. Sniff all the things is good you understand your place in my world naughty running cat intently sniff hand tuxedo cats always looking dapper. ";
        for (int i = 0; i < 10; i++) {
            p.add(new PlainTextMessage("cxk", catIpsum, i % 2));
        }
        s.setViewportView(p);
        f.add(s);
        f.setVisible(true);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 800);
        f.setLocationRelativeTo(null);

    }

}
