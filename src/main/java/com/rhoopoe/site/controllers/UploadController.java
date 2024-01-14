package com.rhoopoe.site.controllers;

import com.rhoopoe.site.entities.PostImage;
import com.rhoopoe.site.services.PostImageService;
import com.rhoopoe.site.utils.image_file_storage.ThumbnailFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("uploads")
@CrossOrigin(value = "http://localhost:5173")
@RequiredArgsConstructor
public class UploadController {
    private final PostImageService postImageService;
    private final ThumbnailFileStorageService thumbnailFileStorageService;

    @PostMapping(path = "images", headers={"content-type=multipart/form-data"}, consumes = "image/*")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        try{
            PostImage uploadedPostImage = postImageService.store(image.getBytes());
            return new ResponseEntity<String>(uploadedPostImage.getPath(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Could not upload image. Try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        byte[] data = postImageService.retrieve(imageName);
        return ResponseEntity.ok().contentType(mediaType).body(data);
    }
    @GetMapping(path = "images/thumbnails/{imageName}")
    public ResponseEntity<byte[]> getImageFromFileSystem(@PathVariable String imageName) {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        try {
            byte[] data = thumbnailFileStorageService.retrieve(imageName);
            return ResponseEntity.ok().contentType(mediaType).body(data);
        } catch (Exception exception) {
            return new ResponseEntity<>( null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
