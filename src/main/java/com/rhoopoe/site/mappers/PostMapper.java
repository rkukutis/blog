package com.rhoopoe.site.mappers;

import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;

import java.util.Base64;

public class PostMapper{

    public static Post dtoToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());
        post.setThumbnail(Base64.getDecoder().decode(postDTO.getThumbnailBase64()));
        return post;
    }
    public static PostDTO EntitytoDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setTitle(post.getTitle());
        postDTO.setThumbnailBase64(new String(Base64.getEncoder().encode(post.getThumbnail())));
        return postDTO;
    }

}
