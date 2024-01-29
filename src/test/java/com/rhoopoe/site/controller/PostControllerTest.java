package com.rhoopoe.site.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class PostControllerTest {

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void configureMockMvc(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void givenGetPosts_whenUser_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk());
    }
}