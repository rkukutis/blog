package com.rhoopoe.site.service.imagestorage;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.exception.ImageProcessingException;
import com.rhoopoe.site.service.imageprocessing.PostPictureProcessingService;
import com.rhoopoe.site.service.imageprocessing.ThumbnailProcessingService;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class ImageFileStorageServiceTest {

    @Value("${app.constants.file-root}")
    private String FILE_ROOT_PATH;

    @Value("${app.constants.host}")
    private String HOST;

    @Value("${app.constants.image-location.post-picture}")
    private String postPicturePath;

    @Value("${app.constants.image-location.thumbnail}")
    private String thumbnailPath;

    @Autowired
    private ImageFileStorageService imageFileStorageService;

    @AfterEach
    public void deleteTestImages() throws IOException {
        Files.deleteIfExists(Path.of(FILE_ROOT_PATH + postPicturePath));
        Files.deleteIfExists(Path.of(FILE_ROOT_PATH + thumbnailPath));
    }


    @Test
    void storeThumbnail() throws IOException, ImageProcessingException {
        byte[] base64 = Files.readAllBytes(Path.of(FILE_ROOT_PATH + "/test-images/base64Image.txt"));
        byte [] decodedImage = Base64.getDecoder().decode(base64);
        String returnString = imageFileStorageService.store(decodedImage, "test.png", ImageRole.THUMBNAIL);
        Path imagePath = Path.of(FILE_ROOT_PATH + thumbnailPath, "test.png");
        boolean exists = Files.isReadable(imagePath);
        assertTrue(exists);
        assertEquals(HOST + "/uploads/images/thumbnails/test.png", returnString);
        byte[] storedImage = imageFileStorageService.retrieve("test.png", ImageRole.THUMBNAIL);
        assertNotNull(storedImage);
        assertNotEquals(0, storedImage.length);
        imageFileStorageService.delete("test.png", ImageRole.THUMBNAIL);
        assertEquals(false, Files.exists(imagePath));
    }

    @Test
    void storeInvalidBase64Thumbnail() throws IOException {
        byte[] base64 = Files.readAllBytes(Path.of(FILE_ROOT_PATH + "/test-images/invalidBase64Text.txt"));
        byte [] decodedImage = Base64.getDecoder().decode(base64);
        assertThrows(ImageProcessingException.class, ()->
                imageFileStorageService.store(decodedImage, "test.png", ImageRole.THUMBNAIL)
        );
    }

    @Test
    void storePostPicture() throws IOException, ImageProcessingException {
        byte[] base64 = Files.readAllBytes(Path.of(FILE_ROOT_PATH + "/test-images/base64Image.txt"));
        byte [] decodedImage = Base64.getDecoder().decode(base64);
        Path imagePath = Path.of(FILE_ROOT_PATH + postPicturePath, "test.png");
        String returnString = imageFileStorageService.store(decodedImage, "test.png", ImageRole.POST_IMAGE);
        boolean exists = Files.isReadable(imagePath);
        assertTrue(exists);
        assertEquals(HOST + "/uploads/images/test.png", returnString);
        byte[] storedImage = imageFileStorageService.retrieve("test.png", ImageRole.POST_IMAGE);
        assertNotNull(storedImage);
        assertNotEquals(0, storedImage.length);
        imageFileStorageService.delete("test.png", ImageRole.POST_IMAGE);
        assertEquals(false, Files.exists(imagePath));
    }
    @Test
    void storeGif() throws IOException, ImageProcessingException {
        byte[] gifBytes = Files.readAllBytes(Path.of(FILE_ROOT_PATH + "/test-images/test-gif.gif"));
        Path imagePath = Path.of(FILE_ROOT_PATH + postPicturePath, "test.gif");
        String returnString = imageFileStorageService.store(gifBytes, "test.gif", ImageRole.POST_IMAGE);
        boolean exists = Files.isReadable(imagePath);
        assertTrue(exists);
        assertEquals(HOST + "/uploads/images/test.gif", returnString);
        byte[] storedImage = imageFileStorageService.retrieve("test.gif", ImageRole.POST_IMAGE);
        assertNotNull(storedImage);
        assertEquals(gifBytes.length, storedImage.length);
        imageFileStorageService.delete("test.gif", ImageRole.POST_IMAGE);
        assertEquals(false, Files.exists(imagePath));
    }

}