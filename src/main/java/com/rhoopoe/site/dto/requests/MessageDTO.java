package com.rhoopoe.site.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
public class MessageDTO {

    @NotBlank
    @Pattern(regexp = "[A-Za-z -]{2,}", message = "Name must have only latin characters!")
    @Length(min = 0, max = 50, message = "Name must be less than 50 characters long!")
    private String senderName;

    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email must be valid!")
    @Length(min = 0, max = 100, message = "Email must be less than 100 characters long!")
    private String senderEmail;

    @NotBlank
    @Pattern(regexp = "[A-Za-z -]{2,}", message = "Message must have only latin characters!")
    @Length(min = 0, max = 200, message = "Email must be less than 100 characters long!")
    private String messageBody;

}
