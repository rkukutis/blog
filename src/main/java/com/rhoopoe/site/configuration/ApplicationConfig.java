package com.rhoopoe.site.configuration;

import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.repositories.PostRepository;
import com.rhoopoe.site.security.AuthUser;
import com.rhoopoe.site.security.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
    private final PostRepository postRepository;
    private final AuthUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ApplicationConfig(PostRepository postRepository,
                             AuthUserRepository userRepository,
                             BCryptPasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initialize(){
      return args -> {
          postRepository.save(new Post("test post 1", "LOREM IPSUM", "IMAGE1"));
          postRepository.save(new Post("test post 2", "LOREM IPSUM", "IMAGE2"));
          postRepository.save(new Post("test post 3", "LOREM IPSUM", "IMAGE3"));

          userRepository.save(new AuthUser("rhoopoe", passwordEncoder.encode("ateitiesprofesionalai")));
      };
    }
}
