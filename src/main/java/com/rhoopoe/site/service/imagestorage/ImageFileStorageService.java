package com.rhoopoe.site.service.imagestorage;

import com.rhoopoe.site.enumerated.ImageType;
import com.rhoopoe.site.service.imageprocessing.PostPictureProcessingService;
import com.rhoopoe.site.service.imageprocessing.ThumbnailProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class ImageFileStorageService {
    private final ThumbnailProcessingService thumbnailProcessingService;
    private final PostPictureProcessingService postPictureProcessingService;

    @Value("${app.constants.file-root}")
    private String FILE_ROOT_PATH;

    @Value("${app.constants.host}")

    private String HOST;

    private String imagePath;

    public String store(byte[] imageBytes, String imageName, ImageType imageType) throws IOException {
        BufferedImage image = null;
        switch (imageType){
            case THUMBNAIL -> {
                image = thumbnailProcessingService.processImage(imageBytes);
                imagePath = thumbnailProcessingService.getImagePath();
            }
            case POST_IMAGE -> {
                image = postPictureProcessingService.processImage(imageBytes);
                imagePath = postPictureProcessingService.getImagePath();
            }
        }
        String path = FILE_ROOT_PATH + imagePath + imageName;
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        ImageIO.write(image, "png", file);
        return HOST + "/uploads/images/" + imageName;
    }
    public byte[] retrieve(String fileName) throws IOException {
        Path path = Path.of(FILE_ROOT_PATH + imagePath + fileName);
        return Files.readAllBytes(path);
    }

    public void delete(String filename) throws IOException {
        Path path = Path.of(FILE_ROOT_PATH + imagePath + filename);
        Files.delete(path);
    }



}
