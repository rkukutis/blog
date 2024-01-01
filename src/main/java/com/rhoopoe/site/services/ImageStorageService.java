package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.Image;
import com.rhoopoe.site.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private final ImageRepository imageRepository;

    public Image store(String imageName, byte[] data){
        Image image = new Image(imageName, data);
        return imageRepository.save(image);
    }
    public byte[] retrieve(String imageName){
        return imageRepository.findByImageNameContainingIgnoreCase(imageName).getData();
    }
}
