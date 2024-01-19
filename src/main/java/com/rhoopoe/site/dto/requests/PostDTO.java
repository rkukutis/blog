package com.rhoopoe.site.dto.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PostDTO {
    @NotBlank
    @Length(max = 200)
    private String title;

    @NotBlank
    @Length(max = 500, min = 100)
    private String subtitle;

    @NotBlank
    private String body;

    @JsonProperty("thumbnail")
    private String thumbnailBase64;
}
