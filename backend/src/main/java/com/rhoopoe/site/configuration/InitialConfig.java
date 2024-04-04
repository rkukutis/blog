package com.rhoopoe.site.configuration;

import com.rhoopoe.site.entity.Theme;
import com.rhoopoe.site.repository.ThemeRepository;
import com.rhoopoe.site.service.security.AuthenticationService;
import com.rhoopoe.site.dto.requests.RegisterDTO;
import com.rhoopoe.site.enumerated.Role;
import com.rhoopoe.site.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Scanner;

@Configuration
@RequiredArgsConstructor
public class InitialConfig {
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
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

    @Bean
    CommandLineRunner addInitialCategories() {
        return args -> {
            try (Scanner scanner = new Scanner(getFileAsIOStream("initialThemes.csv"))) {
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (themeRepository.existsByName(parts[0])) {
                        continue;
                    }
                    Theme newTheme = Theme.builder()
                            .name(parts[0])
                            .colorHex(parts[1])
                            .build();
                    themeRepository.save(newTheme);
                }
            }
        };
    }


    private InputStream getFileAsIOStream(String fileName) {
        InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}
