package com.rhoopoe.site.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post{


    @Id
    @Column(name = "id")
    private UUID uuid;

    @NonNull
    @Setter
    private String title;

    @Column(columnDefinition = "TEXT")
    @NonNull
    @Setter
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    @NonNull
    @Setter
    private String body;

    @Column(name = "thumbnail_image")
    @Setter
    private String thumbnail;

    @ManyToMany
    @Setter
    @JoinTable(name = "post_themes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "theme_id")
    )
    private Set<Theme> themes;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Date modifiedAt;

    @PostPersist
    public void logCreatedAt(){
        this.createdAt = new Date();
        modifiedAt = this.createdAt;
    }
    @PreUpdate
    public void modifiedAt(){
        this.modifiedAt = new Date();
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
