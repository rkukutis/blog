package com.rhoopoe.site.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Post{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NonNull
    @Setter
    private String title;

    @Column(columnDefinition = "TEXT")
    @NonNull
    @Setter
    private String body;

    @Column(name = "thumbnail_image")
    @Setter
    private byte[] thumbnail;

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
        return Objects.equals(title, post.title) && Objects.equals(body, post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body);
    }
}
