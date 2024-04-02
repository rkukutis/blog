package com.rhoopoe.site.configuration;

import com.rhoopoe.site.dto.requests.RegisterDTO;
import com.rhoopoe.site.enumerated.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectoryConfig {

    @Bean
    CommandLineRunner createDirectories(){
        return args -> {
        };
    }
}
