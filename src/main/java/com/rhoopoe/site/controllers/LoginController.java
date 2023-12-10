package com.rhoopoe.site.controllers;

import com.rhoopoe.site.DTOs.LoginDTO;
import com.rhoopoe.site.entities.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<String> createPost(@RequestBody LoginDTO postDTO){
        System.out.println(postDTO);
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(postDTO.getUsername(), postDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Logged in successfully", HttpStatus.OK);
        } catch (BadCredentialsException exception){
            return new ResponseEntity<>("Wrong username or password", HttpStatus.FORBIDDEN);
        }
    }
}
