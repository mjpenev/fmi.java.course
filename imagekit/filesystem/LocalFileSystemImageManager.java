package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

public class LocalFileSystemImageManager implements FileSystemImageManager {
    private static String fileExtension = "";

    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        try {
            if (imageFile == null) {
                throw new IllegalArgumentException("Unsupported image format or invalid image file");
            }
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            if (bufferedImage == null) {
                throw new IllegalArgumentException("Unsupported image format or invalid image file");
            }
            int i = imageFile.getName().lastIndexOf('.');
            if (i > 0) {
                fileExtension = imageFile.getName().substring(i + 1);
            }
            return bufferedImage;
        }
        catch (IOException e) {
            throw new UncheckedIOException("Error appeared when image is loaded.", e);
        }
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if (imagesDirectory == null) {
            throw new IllegalArgumentException("Directory is null.");
        }
        if (!imagesDirectory.isDirectory()) {
            throw new IllegalArgumentException("Provided path should be directory.");
        }
        List<BufferedImage> bufferedImages = new ArrayList<>();
        File[] files = imagesDirectory.listFiles();

        if (files == null) {
            return bufferedImages;
        }
        for (File file : files) {
            if (file.isFile()) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (image != null) {
                        bufferedImages.add(image);
                    }
                }
                catch (IOException e) {
                    throw new UncheckedIOException("An image couldn't be loaded properly.", e);
                }
            }
        }
        return bufferedImages;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image is null");
        }
        try {
            ImageIO.write(image, getTypeOfFile(), imageFile);
        }
        catch (IOException e) {
            throw new UncheckedIOException("Error appeared while saving the file.", e);
        }
    }

    public String getTypeOfFile() {
        return fileExtension;
    }
}
