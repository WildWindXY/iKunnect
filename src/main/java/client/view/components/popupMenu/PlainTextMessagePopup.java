package client.view.components.popupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PlainTextMessagePopup extends JPopupMenu {

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
            JMenuItem translate = new JMenuItem("Translate");
            add(forward);
            add(copyMessage);
            add(translate);
        }
    }

}
