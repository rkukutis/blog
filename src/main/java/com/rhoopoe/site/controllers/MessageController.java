package com.rhoopoe.site.controllers;

import com.rhoopoe.site.DTOs.MessageDTO;
import com.rhoopoe.site.entities.Message;
import com.rhoopoe.site.exceptions.MessageNotFoundException;
import com.rhoopoe.site.mappers.MessageMapper;
import com.rhoopoe.site.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

@GetMapping
public ResponseEntity<List<Message>> getMessages(){
    List<Message> messages =  messageService.getMessages();
    log.debug("Fetching all messages");
    return ResponseEntity.ok().body(messages);
}

@PostMapping
public ResponseEntity<Message> createMessage(@RequestBody @Valid MessageDTO messageDTO){
    Message message = MessageMapper.dtoToEntity(messageDTO);
    Message createdMessage = messageService.createMessage(message);
    return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
}

@DeleteMapping("/{messageUUID}")
public ResponseEntity<String> deleteMessage(@PathVariable UUID messageUUID) throws MessageNotFoundException {
        messageService.deleteMessage(messageUUID);
        return ResponseEntity.noContent().build();
}


}
