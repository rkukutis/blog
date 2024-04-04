package com.rhoopoe.site.repository;

import com.rhoopoe.site.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, UUID> {
    boolean existsByName(String name);
}
