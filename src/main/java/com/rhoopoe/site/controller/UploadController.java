package com.rhoopoe.site.controller;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.service.PostImageService;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("uploads")
@RequiredArgsConstructor
public class UploadController {
    private final PostImageService postImageService;
    private final ImageFileStorageService imageFileStorageService;

    @PostMapping(path = "/images", headers={"content-type=multipart/form-data"}, consumes = "image/*")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) throws IOException {
        PostImage uploadedPostImage = postImageService.store(image.getBytes());
        return ResponseEntity.accepted().body(uploadedPostImage.getPath());
    }
    @GetMapping(path = "/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        byte[] data = postImageService.retrieve(imageName);
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
    @GetMapping(path = "/images/thumbnails/{imageName}")
    public ResponseEntity<byte[]> getImageFromFileSystem(@PathVariable String imageName) throws IOException {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        byte[] data = imageFileStorageService.retrieve(imageName);
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
}
