package com.rhoopoe.site.utils.image_file_storage;

import com.rhoopoe.site.utils.ImageProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class ThumbnailFileStorageService implements ImageFileStorageService {
    private final String thumbnailPath = "/images/thumbnails/";

    public String store(byte[] imageBytes, String imageName) throws IOException  {
        String path = ROOT + thumbnailPath + imageName;
        try {
        BufferedImage image = new ImageProcessing(imageBytes).toImage();
        File file = new File(path);
        ImageIO.write(image, "png", file);
        } catch (IOException exception){
            log.error(path,"Could not write thumbnail to %s");
            throw exception;
        }
        return HOST + "/uploads/images/thumbnails/" + imageName;
    }
    public byte[] retrieve(String fileName) throws IOException {
        Path path = Path.of(ROOT + thumbnailPath + fileName);
        return Files.readAllBytes(path);
    }
}
