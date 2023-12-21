package com.rhoopoe.site.security;

import com.rhoopoe.site.security.user.Role;
import com.rhoopoe.site.security.user.User;
import com.rhoopoe.site.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDTO registerDTO) {
        System.out.println(registerDTO);
        var user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword()));
        var user = repository.findByUsername(authenticationDTO.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Wrong Credentials"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
