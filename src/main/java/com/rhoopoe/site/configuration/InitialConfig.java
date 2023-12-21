package com.rhoopoe.site.configuration;

import com.rhoopoe.site.security.AuthenticationService;
import com.rhoopoe.site.security.RegisterDTO;
import com.rhoopoe.site.security.user.Role;
import com.rhoopoe.site.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitialConfig {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    @Bean
    CommandLineRunner initialize(){
        return args -> {
            if (userRepository.findByUsername("rhoopoe").isEmpty()){
                authenticationService.register(new RegisterDTO("rhoopoe", "ateitiesprofesionalas"));
                userRepository.findByUsername("rhoopoe").get().setRole(Role.ADMIN);
            }
        };
    }
}
