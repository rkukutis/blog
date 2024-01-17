package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.enumerated.ImageType;
import com.rhoopoe.site.repository.ImageRepository;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
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
