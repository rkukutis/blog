package com.rhoopoe.site.controller;

import com.rhoopoe.site.configuration.security.JwtAuthenticationFilter;
import com.rhoopoe.site.service.MessageService;
import com.rhoopoe.site.service.PostImageService;
import com.rhoopoe.site.service.PostService;
import com.rhoopoe.site.service.security.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
class PostControllerTest {

    @MockBean
    private JwtAuthenticationFilter jwtAuthFilter;
    @MockBean
    private AuthenticationProvider authenticationProvider;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private MessageService messageService;
    @MockBean
    private PostService postService;
    @MockBean
    PostImageService postImageService;
    @MockBean UploadController uploadController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenPostNewMessage_whenAuthenticated_thenForbidden() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/posts").with(csrf())
                )
                .andExpect(status().isOk());
    }

}