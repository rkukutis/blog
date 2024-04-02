package com.rhoopoe.site.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class PasswordChangeRequest {

    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}
