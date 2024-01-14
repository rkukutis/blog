package com.rhoopoe.site.utils.image_file_storage;

import com.rhoopoe.site.utils.ImageProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class PostPictureFileStorageService implements  ImageFileStorageService{
    private final String postImagePath = "/images/post-pictures/";

    public String store(byte[] imageBytes, String imageName) throws IOException {
        BufferedImage image = new ImageProcessing(imageBytes).toImage();
        String path = ROOT + postImagePath + imageName;
        File file = new File(path);
        ImageIO.write(image, "png", file);
        return HOST + "/uploads/images/" + imageName;
    }
    public byte[] retrieve(String fileName) throws IOException{
        Path path = Path.of(ROOT + postImagePath + fileName);
        return Files.readAllBytes(path);
    }

    @Override
    public void delete(String filename) throws IOException {
        Path path = Path.of(ROOT + postImagePath + filename);
        Files.delete(path);
    }

}
