package com.rhoopoe.site.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Post{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @NonNull
    private String title;
    @Column(columnDefinition = "TEXT")
    @NonNull
    private String body;
    @Column(name = "image")
    @NonNull
    private String imageUrl;
    @Column(name = "created_at")
    private Date createdAt;


    @PrePersist
    public void logCreatedAt(){
        this.createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(title, post.title) && Objects.equals(body, post.body) && Objects.equals(imageUrl, post.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, imageUrl);
    }
}
