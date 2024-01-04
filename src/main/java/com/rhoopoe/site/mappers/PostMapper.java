package com.rhoopoe.site.mappers;

import com.rhoopoe.site.DTOs.PostDTO;
import com.rhoopoe.site.entities.Post;

public class PostMapper{

    public static Post dtoToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());
        // Thumbnail is null for now
        return post;
    }

}
