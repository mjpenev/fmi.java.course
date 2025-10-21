package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {
    @Override
    public BufferedImage process(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image is null.");
        }
        int originalImageWidth = image.getWidth();
        int originalImageHeight = image.getHeight();
        BufferedImage grayscaledImage = new BufferedImage(originalImageWidth, originalImageHeight, BufferedImage.TYPE_INT_RGB);
        // turn into grayscale image
        for (int i = 0; i < originalImageWidth; i++) {
            for (int j = 0; j < originalImageHeight; j++) {
                int rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int gray = (int)Math.round(red*0.30 + green*0.59 + blue*0.11);
                gray = Math.max(0, Math.min(255, gray));
                int newRGB = (gray << 16) | (gray << 8) | gray;
                grayscaledImage.setRGB(i, j, newRGB);
            }
        }
        // Sobel edge detection algorithm
        // Kernel gx and gy initialization
        int[][] gxKernel = {{-1, 0, 1 }, {-2, 0, 2}, {-1, 0, 1}};
        int[][] gyKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // algorithm
        BufferedImage sobleEdgeImage = new BufferedImage(originalImageWidth, originalImageHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 1; x < originalImageWidth - 1; x++) {
            for (int y = 1; y < originalImageHeight - 1; y++) {
                int gx = 0;
                int gy = 0;

                // apply Sobel kernels
                for (int i = 0; i <= 1; i++) {
                    for (int j = 0; j <= 1; j++) {
                        int rgb = grayscaledImage.getRGB(x + i, y + j);
                        int gray = rgb & 0xff;
                        gx += gray * gxKernel[i + 1][j + 1];
                        gy += gray * gyKernel[i + 1][j + 1];
                    }
                }

                int magnitude = (int)Math.min(255, Math.sqrt(gx * gx + gy * gy));
                int newRGB = (magnitude << 16) | (magnitude << 8) | magnitude;
                sobleEdgeImage.setRGB(x, y, newRGB);
            }
        }

        return sobleEdgeImage;
    }
}
