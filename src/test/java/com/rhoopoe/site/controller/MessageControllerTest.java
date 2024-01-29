package com.rhoopoe.site.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhoopoe.site.configuration.security.JwtAuthenticationFilter;
import com.rhoopoe.site.configuration.security.SecurityConfig;
import com.rhoopoe.site.configuration.security.SecurityFilters;
import com.rhoopoe.site.dto.requests.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@ContextConfiguration(classes = {SecurityConfig.class, SecurityFilters.class, JwtAuthenticationFilter.class})
@WebAppConfiguration
class MessageControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(filterChainProxy))
                .build();
    }

    @Test
    void givenGetMessages_whenNoJWT_thenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpectAll(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void givenPostNewMessage_whenAnonymous_thenCreated() throws Exception {

        MessageDTO messageDTO = new MessageDTO(
                "Test Name", "mail@email.com", "Test Body");
        String json = asJsonString(messageDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/messages")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isCreated());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
