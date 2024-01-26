package com.rhoopoe.site.utility;

import com.rhoopoe.site.exception.ImageProcessingException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageProcessing {
    private BufferedImage image;

    public ImageProcessing(BufferedImage image) {
        this.image = image;
    }
    public ImageProcessing(byte[] image) throws ImageProcessingException {
        this.image = this.byteArrayToImage(image);
    }

    private BufferedImage byteArrayToImage(byte[] bytes) throws ImageProcessingException {
        try (var byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            BufferedImage createdImage =  ImageIO.read(byteArrayInputStream);
            if (createdImage == null) throw new ImageProcessingException();
            return createdImage;
        } catch (Exception exception){
            throw new ImageProcessingException("Error while converting byte array to BufferedImage");
        }
    }
    private byte[] imageToByteArray(BufferedImage image) throws IOException {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
    public ImageProcessing squareCropCenterWidth() {
        if (image.getWidth() >= image.getHeight()) {
            int startX = (image.getWidth() - image.getHeight()) / 2;
            image = image.getSubimage(
                        startX,
                        0,
                        image.getHeight(),
                        image.getHeight()
            );
        } else {
            int startY = (image.getHeight() - image.getWidth()) / 2;
            image = image.getSubimage(
                    0,
                    startY,
                    image.getWidth(),
                    image.getWidth()
            );
        }
        return this;
    }
    public ImageProcessing resize(int height, int width, boolean upscale) {
        if (!upscale && image.getHeight() <= height || image.getWidth() <= width) {
            return this;
        }
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics graphics = resizedImage.createGraphics();
        graphics.drawImage(this.image,0,0,width, height, null);
        graphics.dispose();
        this.image = resizedImage;
        return this;
    }
    public ImageProcessing resize(int height, boolean upscale) {
        if (!upscale && image.getHeight() <= height) {
            return this;
        }
        double factor = (double) height / image.getHeight();
        int newWidth = (int)(image.getWidth() * factor);
        BufferedImage resizedImage = new BufferedImage(newWidth, height, image.getType());
        Graphics graphics = resizedImage.createGraphics();
        graphics.drawImage(this.image,0,0,newWidth, height, null);
        graphics.dispose();
        this.image = resizedImage;
        return this;
    }
    public byte[] toByteArray() throws IOException {
        return this.imageToByteArray(this.image);
    }
    public BufferedImage toImage() {
        return this.image;
    }
}
