package com.rhoopoe.site.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class MessageDTO {

    private String senderName;
    private String senderEmail;
    private String messageBody;

}
