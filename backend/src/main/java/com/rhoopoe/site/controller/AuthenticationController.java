package com.rhoopoe.site.controller;

import com.rhoopoe.site.dto.requests.AuthenticationDTO;
import com.rhoopoe.site.dto.requests.PasswordChangeRequest;
import com.rhoopoe.site.dto.responses.AuthenticationResponse;
import com.rhoopoe.site.entity.User;
import com.rhoopoe.site.service.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /*
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(service.register(registerDTO));
    }
    */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        log.info("Log in attempt. Provided credentials " + authenticationDTO);
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDTO));
    }

    @PatchMapping("/account/password")
    public ResponseEntity<User> changePassword(@AuthenticationPrincipal User requestingUser,
                                               @RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        log.info("Password change attempt. Provided request {}", passwordChangeRequest);
        User updatedUser = authenticationService.changeUserPassword(requestingUser, passwordChangeRequest);
        return ResponseEntity.ok().body(updatedUser);
    }
}
