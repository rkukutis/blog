package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.enumerated.image.ImageRole;
import com.rhoopoe.site.exception.ImageProcessingException;
import com.rhoopoe.site.exception.PostNotFoundException;
import com.rhoopoe.site.repository.PostRepository;
import com.rhoopoe.site.service.imagestorage.ImageFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageFileStorageService imageFileStorageService;

    public Post getPostById(UUID postUUID) throws PostNotFoundException {
        return postRepository.findById(postUUID).orElseThrow(()-> new PostNotFoundException(postUUID));
    }

    @Cacheable(value = "posts")
    public Page<Post> getAllPosts(PageRequest pageRequest, String contains){
        if (contains != null){
            return postRepository.findByTitleContainingIgnoreCase(contains, pageRequest);
        }
        return postRepository.findAll(pageRequest);
    }

    @CacheEvict(value = "posts", allEntries = true)
    public Post createPost(Post post, byte[] thumbnail) throws IOException, ImageProcessingException {
        // 1) save post without thumbnail
        Post savedPost = postRepository.save(post);
        // 2) process thumbnail
        String thumbnailPath = processThumbnail(thumbnail, savedPost);
        // 3) update saved post with path to thumbnail
        savedPost.setThumbnail(thumbnailPath);
        log.debug("Post {} created", savedPost.getUuid());
        return postRepository.save(savedPost);
    }
    @CacheEvict(value = "posts", allEntries = true)
    public Post updatePost(Post updatedPost, UUID postUUID, byte[] thumbnailBytes) throws IOException,
            ImageProcessingException {
        Post postToBeUpdated = postRepository.getReferenceById(postUUID);
        postToBeUpdated.setTitle(updatedPost.getTitle());
        postToBeUpdated.setSubtitle(updatedPost.getSubtitle());
        postToBeUpdated.setThemes(updatedPost.getThemes());
        postToBeUpdated.setBody(updatedPost.getBody());
        // Delete old thumbnail and process new one if new base64 thumbnail is provided
        if (thumbnailBytes.length != 0) {
            imageFileStorageService.delete(postToBeUpdated.getUuid().toString() + ".png", ImageRole.THUMBNAIL);
            String newThumbnailPath = processThumbnail(thumbnailBytes, postToBeUpdated);
            postToBeUpdated.setThumbnail(newThumbnailPath);
        }
        return postRepository.save(postToBeUpdated);
    }


    @CacheEvict(value = "posts", allEntries = true)
    public void deletePost(UUID postID) throws IOException, PostNotFoundException {
        try {
        postRepository.deleteById(postID);
        } catch (IllegalArgumentException exception){
            throw new PostNotFoundException(postID);
        }
        imageFileStorageService.delete(postID + ".png", ImageRole.THUMBNAIL);
    }
    private String processThumbnail(byte[] thumbnailBytes, Post post) throws IOException, ImageProcessingException {
        StringBuilder thumbnailPath = new StringBuilder();
        thumbnailPath.append(imageFileStorageService.store(thumbnailBytes,
                post.getUuid().toString() + ".png", ImageRole.THUMBNAIL));
        return thumbnailPath.toString();
    }

}
