package com.rhoopoe.site.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:5173")
public class AuthenticationController {
    private final AuthenticationService service;

    /*
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(service.register(registerDTO));
    }
    */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO authenticationDTO){
        return ResponseEntity.ok(service.authenticate(authenticationDTO));

    }
}
