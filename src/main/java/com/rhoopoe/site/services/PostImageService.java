package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.PostImage;
import com.rhoopoe.site.enums.ImageType;
import com.rhoopoe.site.repositories.ImageRepository;
import com.rhoopoe.site.services.imagestorage.ImageFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostImageService {
    private final ImageRepository imageRepository;
    private final ImageFileStorageService imageFileStorageService;

    public PostImage store(byte[] imageBytes) throws IOException {
        PostImage postImage = new PostImage();
        PostImage savedImage =  imageRepository.save(postImage);
        String storedName = savedImage.getUuid().toString() + ".png";
        savedImage.setImageName(storedName);
        String path = imageFileStorageService.store(imageBytes, storedName, ImageType.POST_IMAGE);
        savedImage.setPath(path);
        return savedImage;
    }
    public byte[] retrieve(String imageName) throws IOException {
        return imageFileStorageService.retrieve(imageName);
    }
}
