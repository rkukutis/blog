package com.rhoopoe.site.DTOs;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostDTO {
    private String title;
    private String body;
    @JsonProperty("thumbnail")
    private String thumbnailBase64;
}
