package client.view.components.image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageFittingComponent extends JComponent {

    private final BufferedImage image;

    public ImageFittingComponent(String imagePath) throws IOException {
        // Load the image from the file
        image = ImageIO.read(new File(imagePath));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int componentWidth = getWidth();
            int componentHeight = getHeight();

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            double scaleFactorX = (double) componentWidth / imageWidth;
            double scaleFactorY = (double) componentHeight / imageHeight;

            double scaleFactor = Math.min(scaleFactorX, scaleFactorY);

            int scaledWidth = (int) (imageWidth * scaleFactor);
            int scaledHeight = (int) (imageHeight * scaleFactor);

            int x = (componentWidth - scaledWidth) / 2;
            int y = (componentHeight - scaledHeight) / 2;

            g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Fitting Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            try {
                ImageFittingComponent imageComponent = new ImageFittingComponent("src/main/resources/userIcon.jpg");
                frame.add(imageComponent);

            } catch (IOException e) {
                e.printStackTrace();
            }

            frame.setVisible(true);
        });
    }
}
