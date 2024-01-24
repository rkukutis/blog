package com.rhoopoe.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhoopoe.site.configuration.security.SecurityConfig;
import com.rhoopoe.site.dto.requests.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenGetMessages_whenNoJWT_thenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpectAll(status().isForbidden());
    }

    @Test
    @WithMockUser
    void givenPostNewMessage_whenAuthenticated_thenForbidden() throws Exception {

        MessageDTO messageDTO = new MessageDTO(
                "Test Name", "mail@email.com", "Test Body");
        String json = asJsonString(messageDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/messages")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isForbidden());
    }
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
