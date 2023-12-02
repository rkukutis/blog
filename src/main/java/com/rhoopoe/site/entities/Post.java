package com.rhoopoe.site.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Data
public class Post{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String title;
    private String body;
    @Column(name = "image")
    private String imageUrl;
    @Column(name = "created_at")
    private Date createdAt;


    public Post(String title, String body, String imageUrl) {
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

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
