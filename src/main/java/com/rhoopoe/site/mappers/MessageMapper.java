package com.rhoopoe.site.mappers;

import com.rhoopoe.site.DTOs.MessageDTO;
import com.rhoopoe.site.entities.Message;

public class MessageMapper {
    public static Message dtoToEntity(MessageDTO messageDTO){
        return new Message(
                messageDTO.getSenderName(),
                messageDTO.getSenderEmail(),
                messageDTO.getMessageBody()
        );
    }
    public static MessageDTO dtoToEntity(Message message){
        return new MessageDTO(
                message.getSenderName(),
                message.getSenderEmail(),
                message.getMessageBody()
                );
    }
}
