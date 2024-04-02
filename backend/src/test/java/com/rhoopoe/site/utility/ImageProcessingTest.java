package com.rhoopoe.site.utility;

import com.rhoopoe.site.exception.ImageProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ImageProcessingTest {

    private final String FILE_ROOT_PATH = "src/test/resources";
    private ImageProcessing processing;

    @Test
    void givenByteArray_thenCreateImageProcessing() throws IOException, ImageProcessingException {
        Path path = Path.of(FILE_ROOT_PATH + "/test-images/test.jpg");
        byte[] inputBytes = Files.readAllBytes(path);
        ImageProcessing imageProcessing = new ImageProcessing(inputBytes);
        assertNotEquals(inputBytes.length, imageProcessing.toByteArray().length);
    }

    @Test
    void GivenImage_thenResizeByHeightAndWidth() throws IOException, ImageProcessingException {
        Path path = Path.of(FILE_ROOT_PATH + "/test-images/test.jpg");
        byte[] inputBytes = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(inputBytes);
        BufferedImage startingImage = ImageIO.read(is);
        int startingHeight = startingImage.getHeight();
        int startingWidth = startingImage.getWidth();
        int newWidth = 200;
        int newHeight = 100;
        is.close();
        BufferedImage processed = new ImageProcessing(inputBytes)
                .resize(newHeight, newWidth, false)
                .toImage();
        log.debug("Starting image dimensions: {}", startingWidth + "x" +  startingHeight);
        log.debug("Processed image dimensions: {}", processed.getWidth() + "x" +  processed.getHeight());
        assertEquals(newHeight, processed.getHeight());
        assertEquals(newWidth, processed.getWidth());
    }

    @Test
    void GivenWideImage_thenCropByHeight() throws IOException, ImageProcessingException {
        Path path = Path.of(FILE_ROOT_PATH + "/test-images/test.jpg");
        byte[] inputBytes = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(inputBytes);
        BufferedImage startingImage = ImageIO.read(is);
        int startingHeight = startingImage.getHeight();
        int startingWidth = startingImage.getWidth();
        is.close();
        BufferedImage processed = new ImageProcessing(inputBytes)
                .squareCropCenterWidth()
                .toImage();
        log.debug("Starting image dimensions: {}", startingWidth + "x" +  startingHeight);
        log.debug("Processed image dimensions: {}", processed.getWidth() + "x" +  processed.getHeight());

        assertEquals(startingHeight, processed.getHeight());
        assertEquals(startingHeight, processed.getWidth());
    }

    @Test
    void GivenSmallImage_whenResizing_thenDoNothing() throws IOException, ImageProcessingException {
        Path path = Path.of(FILE_ROOT_PATH + "/test-images/test-small.jpg");
        byte[] inputBytes = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(inputBytes);
        BufferedImage startingImage = ImageIO.read(is);
        int startingHeight = startingImage.getHeight();
        int startingWidth = startingImage.getWidth();
        int newHeight = (int) ((int) startingImage.getHeight() * 1.5);
        is.close();
        BufferedImage processed = new ImageProcessing(inputBytes)
                .resize(newHeight, false)
                .toImage();

        assertEquals(startingHeight, processed.getHeight());
        assertEquals(startingWidth, processed.getWidth());
    }

    @Test
    void GivenTallImage_thenCropByWidth() throws IOException, ImageProcessingException {
        Path path = Path.of(FILE_ROOT_PATH + "/test-images/test-tall.jpg");
        byte[] inputBytes = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(inputBytes);
        BufferedImage startingImage = ImageIO.read(is);
        int startingWidth = startingImage.getWidth();
        is.close();
        BufferedImage processed = new ImageProcessing(inputBytes)
                .squareCropCenterWidth()
                .toImage();

        assertEquals(startingWidth, processed.getHeight());
        assertEquals(startingWidth, processed.getWidth());
    }

}