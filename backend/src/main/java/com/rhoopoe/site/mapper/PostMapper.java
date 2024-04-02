package com.rhoopoe.site.mapper;

import com.rhoopoe.site.dto.requests.PostDTO;
import com.rhoopoe.site.entity.Post;
import com.rhoopoe.site.enumerated.PostTheme;

import java.util.stream.Collectors;

public class PostMapper{

    public static Post dtoToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());
        post.setThemes(
                postDTO.getThemes().stream()
                        .map(String::toUpperCase)
                        .map(PostTheme::valueOf)
                        .collect(Collectors.toSet())
        );
        post.setSubtitle(postDTO.getSubtitle());
        // Thumbnail is null for now
        return post;
    }

}
