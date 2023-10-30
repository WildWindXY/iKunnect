package view;

import javax.swing.*;
import java.awt.event.KeyEvent;
import org.junit.Test;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class MySwingComponentTest {

    @Test
    public void testMySwingComponent() {

        // Create the UI component
        MySwingComponent component = new MySwingComponent();
        JFrame frame = new JFrame();
        frame.setContentPane(component);
        frame.pack();
        frame.setVisible(true);

        // Simulate user interaction
        JTextField textField = component.getTextField();

        // Create and dispatch KeyEvents to the UI
        KeyEvent event = new KeyEvent(
                textField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'A'); // Simulate typing 'A'

        textField.dispatchEvent(event);

        // Pause execution for a second
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Perform assertions or validations
        String text = textField.getText();
        System.out.println("Text in the text field: " + text);
        assertEquals("A", text);

        // Simulate more user interaction
        KeyEvent event2 = new KeyEvent(
                textField,
                KeyEvent.KEY_TYPED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                'B'); // Simulate typing 'B'

        textField.dispatchEvent(event2);

        // Pause execution for 3 seconds
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Perform additional assertions or validations
        String updatedText = textField.getText();
        System.out.println("Updated text in the text field: " + updatedText);
        assertEquals("AB", updatedText);
    }
}
