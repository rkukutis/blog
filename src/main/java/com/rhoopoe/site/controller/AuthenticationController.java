package com.rhoopoe.site.controller;

import com.rhoopoe.site.dto.requests.AuthenticationDTO;
import com.rhoopoe.site.dto.responses.AuthenticationResponse;
import com.rhoopoe.site.service.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
//@CrossOrigin("http://localhost:5173/")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    /*
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(service.register(registerDTO));
    }
    */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        log.info("Log in attempt. Provided credentials " + authenticationDTO);
        return ResponseEntity.ok(service.authenticate(authenticationDTO));
    }
}
