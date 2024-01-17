package com.rhoopoe.site.configuration;

import com.rhoopoe.site.security.AuthenticationService;
import com.rhoopoe.site.security.RegisterDTO;
import com.rhoopoe.site.security.user.Role;
import com.rhoopoe.site.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitialConfig {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Value("${app.constants.secrets.username}")
    private String username;
    @Value("${app.constants.secrets.password}")
    private String password;
    @Bean
    CommandLineRunner initialize(){
        return args -> {
            if (userRepository.findByUsername(username).isEmpty()){
                authenticationService.register(new RegisterDTO(username, password));
                userRepository.findByUsername(username).get().setRole(Role.ADMIN);
            }
        };
    }
}
