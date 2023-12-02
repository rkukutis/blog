package com.rhoopoe.site.configuration;

import com.rhoopoe.site.entities.Post;
import com.rhoopoe.site.repositories.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    private final PostRepository postRepository;

    public ApplicationConfig(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Bean
    CommandLineRunner initialize(){
      return args -> {
          postRepository.save(new Post("test post 1", "LOREM IPSUM", "IMAGE1"));
          postRepository.save(new Post("test post 2", "LOREM IPSUM", "IMAGE2"));
          postRepository.save(new Post("test post 3", "LOREM IPSUM", "IMAGE3"));
      };
    }
}
