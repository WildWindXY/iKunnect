package com.xiaoheizi.client.view.components.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageFittingComponent extends JComponent {

    private final BufferedImage image;

    public ImageFittingComponent(InputStream inputStream) throws IOException {
        // Load the image from the file
        image = ImageIO.read(inputStream);
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
}
