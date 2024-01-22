package com.rhoopoe.site.service.imagestorage;

import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.service.imageprocessing.PostPictureProcessingService;
import com.rhoopoe.site.service.imageprocessing.ThumbnailProcessingService;
import com.rhoopoe.site.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageFileStorageService {
    private final ThumbnailProcessingService thumbnailProcessingService;
    private final PostPictureProcessingService postPictureProcessingService;

    @Value("${app.constants.file-root}")
    private String FILE_ROOT_PATH;

    @Value("${app.constants.host}")
    private String HOST;

    private String imagePath;

    public String store(byte[] imageBytes, String imageName, ImageRole imageRole) throws IOException {

        BufferedImage image = null;
        String imageExtension = FileUtils.getFileExtension(imageName);
        switch (imageRole){
            case THUMBNAIL -> {
                image = thumbnailProcessingService.processImage(imageBytes, imageExtension);
                imagePath = thumbnailProcessingService.getImagePath();
            }
            case POST_IMAGE -> {
                // Processing gifs is a pain
                if (FileUtils.getFileExtension(imageName).equalsIgnoreCase("gif")) {
                    writeGif(imageBytes, imageName);
                    return HOST + "/uploads/images/" + imageName;
                }
                image = postPictureProcessingService.processImage(imageBytes, imageExtension);
                imagePath = postPictureProcessingService.getImagePath();
            }
        }
        Path path = Path.of(FILE_ROOT_PATH + imagePath, imageName);
        File file = new File(path.toString());
        if (!file.exists()){
            file.mkdirs();
        }
        ImageIO.write(image, FileUtils.getFileExtension(imageName), file);
        return HOST + "/uploads/images/" + (imageRole == ImageRole.THUMBNAIL? "thumbnails/" : "") + imageName;
    }
    public byte[] retrieve(String fileName, ImageRole imageRole) throws IOException {
        if (imageRole == ImageRole.POST_IMAGE) {
            return Files.readAllBytes(
                    Path.of(FILE_ROOT_PATH + postPictureProcessingService.getImagePath(), fileName)
            );
        }
        return Files.readAllBytes(
                Path.of(FILE_ROOT_PATH + thumbnailProcessingService.getImagePath(), fileName)
        );
    }

    public void delete(String filename, ImageRole role) throws IOException {
        Path path = null;
        switch (role){
            case THUMBNAIL -> path = Path.of(FILE_ROOT_PATH + thumbnailProcessingService.getImagePath(), filename);
            case POST_IMAGE -> path = Path.of(FILE_ROOT_PATH + postPictureProcessingService.getImagePath(), filename);
        }
        Files.delete(path);
    }

    private void writeGif(byte[] imageBytes, String name) throws IOException {
        try (InputStream is = new ByteArrayInputStream(imageBytes)){
            Path storagePath = Path.of(FILE_ROOT_PATH + "/images/post-pictures", name);
            log.debug("Storing GIF in {}", storagePath.toString());
            Files.write(storagePath, imageBytes);
        }
    }
}
