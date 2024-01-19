package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.repository.ImageRepository;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import com.rhoopoe.site.utility.FileUtils;
import com.rhoopoe.site.utility.ImageProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@RequiredArgsConstructor
public class PostImageService {
    private final ImageRepository imageRepository;
    private final ImageFileStorageService imageFileStorageService;

    public PostImage store(byte[] imageBytes, String imageName) throws IOException {
        PostImage postImage = new PostImage();
        PostImage savedImage =  imageRepository.save(postImage);
        String imageExtension = "." + FileUtils.getFileExtension(imageName);
        String storedName = savedImage.getUuid().toString() + imageExtension;
        savedImage.setImageName(storedName);
        String savedPath = imageFileStorageService.store(imageBytes, storedName, ImageRole.POST_IMAGE);
        savedImage.setPath(savedPath);
        return savedImage;
    }
    public byte[] retrieve(String imageName, ImageRole imageRole) throws IOException {
        return imageFileStorageService.retrieve(imageName, imageRole);
    }


}
