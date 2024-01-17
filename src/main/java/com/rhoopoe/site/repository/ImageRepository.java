package com.rhoopoe.site.repository;

import com.rhoopoe.site.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<PostImage, UUID> {

    PostImage findByImageNameContainingIgnoreCase(String imageName);
}
