package com.rhoopoe.site.repository;

import com.rhoopoe.site.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<PostImage, UUID> {

    PostImage findByImageNameContainingIgnoreCase(String imageName);
}
