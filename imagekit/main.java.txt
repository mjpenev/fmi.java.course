import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection.SobelEdgeDetection;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.FileSystemImageManager;
import bg.sofia.uni.fmi.mjt.imagekit.filesystem.LocalFileSystemImageManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystemImageManager fsImageManager = new LocalFileSystemImageManager();

        try {
            System.out.println("Provide file name to process:" + System.lineSeparator());
            Scanner sc = new Scanner(System.in);
            String fileName = "";
            while (!fileName.equals("end")) {
                fileName = sc.next();
                if (fileName.equals("end")) {
                    break;
                }
                BufferedImage image = fsImageManager.loadImage(new File(fileName));
                ImageAlgorithm sobelEdgeAlgorithm = new SobelEdgeDetection();
                ImageAlgorithm grayScaleAlgorithm = new LuminosityGrayscale();
                BufferedImage sobelEdgeImage = sobelEdgeAlgorithm.process(image);
                BufferedImage grayScaleImage = grayScaleAlgorithm.process(image);
                int i = fileName.lastIndexOf('.');
                String fileExtension = "";
                String nameWihoutExtension = "";
                if (i > 0) {
                    nameWihoutExtension = fileName.substring(0, i);
                    fileExtension = fileName.substring(i + 1);
                }
                fsImageManager.saveImage(grayScaleImage, new File(nameWihoutExtension + "-grayScaledImage." + fileExtension));
                fsImageManager.saveImage(sobelEdgeImage, new File(nameWihoutExtension + "-sobelEdgeDetection." + fileExtension));
                System.out.println("Done.");
            }
        }
        catch (IOException e) {
            throw new UncheckedIOException("Error appeared during main.", e);
        }

    }
}