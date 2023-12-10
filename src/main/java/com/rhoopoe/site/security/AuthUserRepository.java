package com.rhoopoe.site.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    public Optional<AuthUser> findByUsername(String username);
}
