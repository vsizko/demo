package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * First approach to image processing
 *
 * Class ImageIO is used to read data from image file.
 * Since Java 1.6 supported image formats are: GIF, JPEG, PNG, BMP and WBMP,
 * so this formats are supported in our application.
 */
public class App {

    private static final String OUTPUT_FILE_NAME = "image.jpg";

    public static void main( String[] args ) {
        BufferedImage gray = processingImage(args);

        // save to file
        File outputFile = new File(OUTPUT_FILE_NAME);
        try {
            ImageIO.write(gray, "jpg", outputFile);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write file");
        }

        System.out.println("Path to directory image: " + System.getProperty("user.dir"));
        System.out.println("File name: " + OUTPUT_FILE_NAME);
    }

    private static BufferedImage processingImage(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough parameters");
        }

        if (args[0] == null) {
            throw new IllegalArgumentException("Path to image file is not specified");
        }

        if (args[1] == null) {
            throw new IllegalArgumentException("Width parameter is not specified");
        }

        if (args[2] == null) {
            throw new IllegalArgumentException("Height parameter is not specified");
        }

        // loading image
        BufferedImage img;
        if (args[0].startsWith("http")) {
            // load from url
            URL url;
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Illegal url");
            }
            try {
                img = ImageIO.read(url);
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot read image from url");
            }
        } else {
            // load from file
            try {
                img = ImageIO.read(new File(args[0]));
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot read image from file");
            }
        }

        if (img == null) {
            throw new IllegalStateException("Image is empty");
        }

        // resize
        if (!String.valueOf(img.getWidth()).equals(args[1]) ||
                !String.valueOf(img.getHeight()).equals(args[2])) {
            img = resize(img, Integer.valueOf(args[1]), Integer.valueOf(args[2]));
        }

//        // doing gray scale
//        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
//        LookupOp lookupOp = new LookupOp(new GrayscaleLookupTable(), null);
//        lookupOp.filter(img, gray);

        // doing gray scale
        return rgbToGrayscale(img);
    }

    /**
     * Java-converting to gray scale
     */
    private static BufferedImage rgbToGrayscale(BufferedImage img) {
        BufferedImage grayscale = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        ColorConvertOp op = new ColorConvertOp(
                img.getColorModel().getColorSpace(),
                grayscale.getColorModel().getColorSpace(), null);
        op.filter(img, grayscale);

        return grayscale;

    }

    /**
     * Resizing image to custom width and height
     */
    private static BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // maybe SCALE_DEFAULT will be good
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
