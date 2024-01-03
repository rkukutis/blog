package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.PostImage;
import com.rhoopoe.site.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private final ImageRepository imageRepository;

    public PostImage store(String imageName, byte[] data) {
        PostImage postImage = new PostImage(imageName, data);
        return imageRepository.save(postImage);
    }
    public byte[] retrieve(String imageName){
        return imageRepository.findByImageNameContainingIgnoreCase(imageName).getBytes();
    }
}
