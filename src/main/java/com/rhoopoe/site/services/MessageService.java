package com.rhoopoe.site.services;

import com.rhoopoe.site.entities.Message;
import com.rhoopoe.site.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message getMessage(UUID messageId) throws Exception{
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isEmpty()) {
            throw new Exception("Message not found");
        };
        return message.get();
    }

    public Message createMessage(Message message) throws Exception{
        messageRepository.save(message);
        return message;
    }
    public List<Message> getMessages(){
        return messageRepository.findAll();
    }
    public void deleteMessage(UUID messageId) throws Exception{
        messageRepository.deleteById(messageId);
    }
}
