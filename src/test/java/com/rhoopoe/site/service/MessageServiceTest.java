package com.rhoopoe.site.service;

import com.rhoopoe.site.entity.Message;
import com.rhoopoe.site.repository.MessageRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class MessageServiceTest {

    private Message testMessage;

    @MockBean
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Captor
    ArgumentCaptor<Message> messageCaptor;

    @BeforeEach
    public void setup(){
        this.testMessage = new Message("test", "test@mail.com", "test body");
    }


    @Test
    void createMessage() {
        Message createdMessage = messageService.createMessage(testMessage);
        verify(messageRepository, times(1)).save(testMessage);
        verify(messageRepository).save(messageCaptor.capture());
        assertEquals(testMessage, messageCaptor.getValue());
    }

}