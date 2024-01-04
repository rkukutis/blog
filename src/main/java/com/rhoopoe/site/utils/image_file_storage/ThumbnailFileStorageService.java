package com.rhoopoe.site.utils.image_file_storage;

import com.rhoopoe.site.utils.ImageProcessing;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ThumbnailFileStorageService implements ImageFileStorageService {
    private final String thumbnailPath = "/images/thumbnails/";

    public String store(byte[] imageBytes, String imageName) throws IOException {
        BufferedImage image = new ImageProcessing(imageBytes).toImage();
        String path = root + thumbnailPath + imageName;
        File file = new File(path);
        ImageIO.write(image, "png", file);
        return host + "/uploads/images/thumbnails/" + imageName;
    }
    public byte[] retrieve(String fileName){
        byte[] bytes = null;
        try {
            Path path = Path.of(root + thumbnailPath + fileName);
            bytes = Files.readAllBytes(path);
        } catch (IOException exception){
            // TODO: Add default thumbnail
            bytes = new byte[0];
        }
        return bytes;
    }
}
