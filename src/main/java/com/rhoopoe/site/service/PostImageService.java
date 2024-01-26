package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.exception.ImageProcessingException;
import com.rhoopoe.site.repository.ImageRepository;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import com.rhoopoe.site.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;



@Service
@RequiredArgsConstructor
public class PostImageService {
    private final ImageRepository imageRepository;
    private final ImageFileStorageService imageFileStorageService;

    public PostImage store(byte[] imageBytes, String imageName) throws IOException, ImageProcessingException {
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
