package com.rhoopoe.site.repositories;

import com.rhoopoe.site.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
