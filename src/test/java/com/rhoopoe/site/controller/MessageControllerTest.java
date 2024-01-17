package com.rhoopoe.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenGetMessages_whenNoJWT_thenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpectAll(status().isForbidden());
    }

    /*
    TODO: Does not work because default security config is used,
      which results in 403 every time
    @Test
    void givenPostNewMessage_whenNoJWT_thenCreated() throws Exception {

        MessageDTO messageDTO = new MessageDTO(
                "name", "email", "body");
        String json = asJsonString(messageDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/messages")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpectAll(status().isCreated());
    }
     */
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
