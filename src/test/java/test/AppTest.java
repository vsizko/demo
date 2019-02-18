package test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author vsizko on 16.02.19.
 */
public class AppTest {

    private static final String TEST_FILE_NAME = "test.jpg";
    private static final String ACTUAL_FILE_NAME = "image.jpg";

    @Test
    public void mainTest() {
        BufferedImage expectedImage;
        try {
            expectedImage = ImageIO.read(new File(TEST_FILE_NAME));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read test file");
        }

        String[] args = {"https://i.ibb.co/g91HtDZ/south-park.jpg", "400", "300"};
        App.main(args);

        BufferedImage actualImage;
        try {
            actualImage = ImageIO.read(new File(ACTUAL_FILE_NAME));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read actual file");
        }

        //this gets the actual Raster data as a byte array
        byte[] expectedArray = ((DataBufferByte) expectedImage.getData().getDataBuffer()).getData();
        byte[] actualArray = ((DataBufferByte) actualImage.getData().getDataBuffer()).getData();

        assertArrayEquals(expectedArray, actualArray);
    }
}