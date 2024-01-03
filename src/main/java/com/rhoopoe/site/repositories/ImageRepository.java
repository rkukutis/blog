package com.rhoopoe.site.repositories;

import com.rhoopoe.site.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<PostImage, UUID> {

    PostImage findByImageNameContainingIgnoreCase(String imageName);
}
