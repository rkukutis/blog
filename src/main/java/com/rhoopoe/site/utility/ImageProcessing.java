package com.rhoopoe.site.utility;

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
    public ImageProcessing(byte[] image) throws IOException {
        this.image = this.byteArrayToImage(image);
    }

    private BufferedImage byteArrayToImage(byte[] bytes) throws IOException {
        try (var byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            return ImageIO.read(byteArrayInputStream);
        }
    }
    private byte[] imageToByteArray(BufferedImage image) throws IOException {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
    public ImageProcessing squareCropCenterWidth() {
        this.image = this.image.getSubimage(
                    (int) (image.getWidth() * 0.25),
                    0,
                    image.getWidth() / 2,
                    image.getWidth() / 2);
            return this;
    }
    public ImageProcessing resize(int height, int width) {
        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
        Graphics graphics = resizedImage.createGraphics();
        graphics.drawImage(this.image,0,0,width, height, null);
        graphics.dispose();
        this.image = resizedImage;
        return this;
    }
    public ImageProcessing resize(int height) {
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
