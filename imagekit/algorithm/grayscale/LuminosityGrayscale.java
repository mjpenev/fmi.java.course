package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

public class LuminosityGrayscale implements GrayscaleAlgorithm {
    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image is null.");
        }
        BufferedImage graleScaledImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                // Luminosity algorithm
                int gray = (int)Math.round(red*0.21 + green*0.72 + blue*0.07);
                gray = Math.max(0, Math.min(255, gray));

                int newRGB = (gray << 16) | (gray << 8) | gray;
                graleScaledImage.setRGB(i, j, newRGB);
            }
        }
        return graleScaledImage;
    }
}