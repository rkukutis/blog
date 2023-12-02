package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.repositories.PostRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getAllPosts(PageRequest pageRequest){
        Page<Post> postPage = postRepository.findAll(pageRequest);
        return postPage;
    }
    public Post createPost(Post post){
        if (postRepository.exists(Example.of(post))){
            return null;
        }
        return postRepository.save(post);
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
