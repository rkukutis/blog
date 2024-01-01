package com.rhoopoe.site.repositories;

import com.rhoopoe.site.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    Image findByImageNameContainingIgnoreCase(String imageName);
}
