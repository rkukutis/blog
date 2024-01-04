package com.rhoopoe.site.utils.image_file_storage;

import com.rhoopoe.site.utils.ImageProcessing;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostPictureFileStorageService implements  ImageFileStorageService{
    private final String postImagePath = "/images/post-pictures/";

    public String store(byte[] imageBytes, String imageName) throws IOException {
        BufferedImage image = new ImageProcessing(imageBytes).toImage();
        String path = root + postImagePath + imageName;
        File file = new File(path);
        ImageIO.write(image, "png", file);
        return host + "/uploads/images/" + imageName;
    }
    public byte[] retrieve(String fileName){
        byte[] bytes = null;
        try {
            Path path = Path.of(root + postImagePath + fileName);
            bytes = Files.readAllBytes(path);
        } catch (IOException exception){
            // TODO: Add default post picture
            bytes = new byte[0];
        }
        return bytes;
    }
}
