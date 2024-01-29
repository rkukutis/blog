package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.exception.ImageProcessingException;
import com.rhoopoe.site.repository.PostRepository;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private ImageFileStorageService imageFileStorageService;


    @Test
    void givenNewPost_whenSavePost_thenPostHasThumbnailURL() throws IOException, ImageProcessingException {
        Post post = new Post("TEST TITLE","TEST SUBTITLE", "TEST BODY");
        Path testFile = Paths.get("src/test/resources/test-images/base64Image.txt");
        String thumbnail = Files.readString(testFile);
        byte[] bytes = Base64.getDecoder().decode(thumbnail);
        Post savedPost = postService.createPost(post, bytes);
        assertNotNull(savedPost.getUuid());
        assertNotNull(savedPost.getCreatedAt());
        assertEquals("https://rhoopoe.com/api/uploads/images/thumbnails/" + savedPost.getUuid() + ".png",
                savedPost.getThumbnail());
        imageFileStorageService.delete(savedPost.getUuid() + ".png", ImageRole.THUMBNAIL);
    }

}