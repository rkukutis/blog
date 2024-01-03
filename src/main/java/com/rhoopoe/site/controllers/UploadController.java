package com.rhoopoe.site.controllers;

import com.rhoopoe.site.entities.PostImage;
import com.rhoopoe.site.services.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("uploads")
@RequiredArgsConstructor
public class UploadController {
    private final ImageStorageService imageStorage;

    @PostMapping(path = "images", headers={"content-type=multipart/form-data"}, consumes = "image/*")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        try{
            PostImage uploadedPostImage = imageStorage.store(file.getOriginalFilename(), file.getBytes());
            return new ResponseEntity<>(uploadedPostImage.getImageName(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Could not upload image. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        try {
            byte[] data = imageStorage.retrieve(imageName);
            return ResponseEntity.ok().contentType(mediaType).body(data);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
