package com.rhoopoe.site.controller;

import com.rhoopoe.site.entity.PostImage;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.service.PostImageService;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import com.rhoopoe.site.utility.FileUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Objects;

@RestController
@RequestMapping("uploads")
@Slf4j
@CrossOrigin("http://localhost:5173/")
@RequiredArgsConstructor
public class UploadController {
    private final PostImageService postImageService;
    private final ImageFileStorageService imageFileStorageService;

    @PostMapping(path = "/images", headers={"content-type=multipart/form-data"}, consumes = "image/*")
    public ResponseEntity<String> uploadImage(@RequestParam @NonNull MultipartFile image) throws IOException {
        PostImage uploadedPostImage = postImageService.store(image.getBytes(), image.getOriginalFilename());
        log.info("Returning uploaded image path: {}", uploadedPostImage.getPath());
        return ResponseEntity.created(URI.create(uploadedPostImage.getPath()))
                .body(uploadedPostImage.getPath());
    }
    @GetMapping(path = "/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        String extension = FileUtils.getFileExtension(imageName);
        MediaType mediaType = extension.equals("gif") ? MediaType.IMAGE_GIF : MediaType.IMAGE_JPEG;
        byte[] data = postImageService.retrieve(imageName, ImageRole.POST_IMAGE);
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
    @GetMapping(path = "/images/thumbnails/{imageName}")
    public ResponseEntity<byte[]> getImageFromFileSystem(@PathVariable String imageName) throws IOException {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        byte[] data = imageFileStorageService.retrieve(imageName, ImageRole.THUMBNAIL);
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
}
