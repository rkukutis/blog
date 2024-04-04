package com.rhoopoe.site.dto.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.rhoopoe.site.entity.Theme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

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

    @NotEmpty
    Set<Theme> themes;

    @JsonProperty("thumbnail")
    private String thumbnailBase64;


}
