package com.rhoopoe.site.service.security;

import com.rhoopoe.site.dto.requests.AuthenticationDTO;
import com.rhoopoe.site.dto.requests.PasswordChangeRequest;
import com.rhoopoe.site.dto.requests.RegisterDTO;
import com.rhoopoe.site.enumerated.Role;
import com.rhoopoe.site.entity.User;
import com.rhoopoe.site.exception.AccountException;
import com.rhoopoe.site.repository.UserRepository;
import com.rhoopoe.site.dto.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDTO registerDTO) {
        var user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User changeUserPassword(User requestingUser, PasswordChangeRequest passwordChangeRequest) {
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), requestingUser.getPassword())) {
            throw new AccountException("Provided current password does not match stored one");
        }
        if (passwordEncoder.matches(passwordChangeRequest.getNewPassword(), requestingUser.getPassword())) {
            throw new AccountException("Provided new password matches current one");
        }
        requestingUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        return userRepository.save(requestingUser);
    }

    public AuthenticationResponse authenticate(AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword()));
        var user = userRepository.findByUsername(authenticationDTO.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Wrong Credentials"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
