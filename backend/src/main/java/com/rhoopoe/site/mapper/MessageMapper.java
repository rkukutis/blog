package com.rhoopoe.site.mapper;

import com.rhoopoe.site.dto.requests.MessageDTO;
import com.rhoopoe.site.entity.Message;

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
