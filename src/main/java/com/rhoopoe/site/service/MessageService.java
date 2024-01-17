package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.Message;
import com.rhoopoe.site.exception.MessageNotFoundException;
import com.rhoopoe.site.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message getMessage(UUID messageId) throws MessageNotFoundException{
        return messageRepository.findById(messageId)
                .orElseThrow(()-> new MessageNotFoundException("Message " + messageId + " not found!"));
    }

    @CacheEvict(value = "messages", allEntries = true)
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Cacheable(value = "messages")
    public List<Message> getMessages(){
        log.debug("Fetching messages from database");
        return messageRepository.findAll();
    }

    @CacheEvict(value = "messages", allEntries = true)
    public void deleteMessage(UUID messageId) throws MessageNotFoundException{
        try {
        messageRepository.deleteById(messageId);
        log.debug("Deleted message {}", messageId);
        } catch (IllegalArgumentException exception) {
            throw new MessageNotFoundException("Could not delete message that does not exist");
        }
    }
}
