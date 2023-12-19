package com.rhoopoe.site.mappers;

import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;

public class PostMapper{

    public static Post dtoToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());
        post.setImageUrl("default.jpg");
        return post;
    }
    public static PostDTO EntitytoDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
}
