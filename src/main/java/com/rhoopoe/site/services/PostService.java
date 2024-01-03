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
    public Post createPost(Post post){
        byte[] processedThumbnail = null;
        try {
            processedThumbnail = new ImageProcessing(post.getThumbnail())
                    .squareCropCenterWidth()
                    .resize(100,100)
                    .toByteArray();
        } catch (IOException exception) {
            // TODO: get default thumbnail image
            processedThumbnail = new byte[0];
        }
        post.setThumbnail(processedThumbnail);
        Post savedPost = postRepository.save(post);
        try {
            String fileName = savedPost.getUuid().toString() + ".jpg";
            ImageFileStorage.storeThumbnail(processedThumbnail, fileName);
        } catch (IOException ignored) {}
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
