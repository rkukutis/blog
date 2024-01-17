package com.rhoopoe.site.mapper;

import com.rhoopoe.site.dto.requests.PostDTO;
import com.rhoopoe.site.entity.Post;

public class PostMapper{

    public static Post dtoToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());
        post.setSubtitle(postDTO.getSubtitle());
        // Thumbnail is null for now
        return post;
    }

}
