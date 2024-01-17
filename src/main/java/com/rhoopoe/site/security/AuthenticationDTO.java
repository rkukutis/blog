package com.rhoopoe.site.security;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@ToString
public class AuthenticationDTO {

    @NotBlank(message = "username can not be blank")
    @Length(min = 0, max = 100)
    private final String username;

    @NotBlank(message = "password can not be blank")
    @Length(min = 0, max = 100)
    private final String password;
}
