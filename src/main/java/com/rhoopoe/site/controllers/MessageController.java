package com.rhoopoe.site.controllers;

import com.rhoopoe.site.DTOs.MessageDTO;
import com.rhoopoe.site.entities.Message;
import com.rhoopoe.site.mappers.MessageMapper;
import com.rhoopoe.site.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

@GetMapping
    public ResponseEntity<List<Message>> getMessages(){
    List<Message> messages =  messageService.getMessages();
    return new ResponseEntity<>(messages, HttpStatus.OK);
}

@PostMapping
public ResponseEntity<Message> createMessage(@RequestBody MessageDTO messageDTO){
    Message message = MessageMapper.dtoToEntity(messageDTO);
    try {
    Message createdMessage = messageService.createMessage(message);
    return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    } catch (Exception e){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("{messageUUID}")
public ResponseEntity<String> deleteMessage(@PathVariable UUID messageUUID){
    try{
        messageService.getMessage(messageUUID);
        messageService.deleteMessage(messageUUID);
        return new ResponseEntity<>("Message " + messageUUID + " deleted", HttpStatus.OK);
    } catch (Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


}
