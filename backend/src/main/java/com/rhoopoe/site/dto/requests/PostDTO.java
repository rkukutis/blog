package com.rhoopoe.site.dto.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
public class PostDTO {
    @NotBlank
    @Length(max = 200)
    private String title;

    @NotBlank
    @Length(max = 500)
    private String subtitle;

    @NotBlank
    private String body;

    @NonNull
    private List<String> themes;

    @JsonProperty("thumbnail")
    private String thumbnailBase64;


}
