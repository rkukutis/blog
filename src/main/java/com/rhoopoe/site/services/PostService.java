package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.repositories.PostRepository;
import com.rhoopoe.site.utils.ImageFileStorage;
import com.rhoopoe.site.utils.ImageProcessing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(UUID postUUID){
        Optional<Post> post = postRepository.findById(postUUID);
         return post.orElseThrow(() -> new NoSuchElementException("Post " + postUUID.toString() + " not found"));
    }

    public Page<Post> getAllPosts(PageRequest pageRequest, String contains){
        if (contains != null){
            return postRepository.findByTitleContainingIgnoreCase(contains, pageRequest);
        }
        return postRepository.findAll(pageRequest);
    }
    public Post createPost(Post post, byte[] thumbnail){
        // 1) save post without thumbnail
        Post savedPost = postRepository.save(post);
        // 2) process thumbnail
        String thumbnailPath = null;
        try {
            byte[] processedThumbnail = new ImageProcessing(thumbnail)
                    .squareCropCenterWidth()
                    .resize(200,200)
                    .toByteArray();
            thumbnailPath = ImageFileStorage.storeThumbnail(processedThumbnail,
                    savedPost.getUuid().toString() + ".png");
        } catch (IOException exception) {
            throw new RuntimeException("Error occurred while processing thumbnail");
        }
        savedPost.setThumbnail(thumbnailPath);
        return savedPost;
    }
    public Post updatePost(Post updatedPost, UUID postUUID){
        Post postToBeUpdated = postRepository.getReferenceById(postUUID);
        postToBeUpdated.setTitle(updatedPost.getTitle());
        postToBeUpdated.setBody(updatedPost.getBody());
        return postRepository.save(postToBeUpdated);
    }

    public void deletePost(UUID postID){
        postRepository.deleteById(postID);
    }


}
