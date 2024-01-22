package com.rhoopoe.site.entity;


import com.rhoopoe.site.enumerated.PostTheme;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.*;

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
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    @NonNull
    @Setter
    private String body;

    @Column(name = "thumbnail_image")
    @Setter
    private String thumbnail;

    @ElementCollection(targetClass = PostTheme.class)
    @JoinTable(name = "post_themes", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "themes", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<PostTheme> themes = new HashSet<>();

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @PostPersist
    public void logCreatedAt(){
        this.createdAt = new Date();
        modifiedAt = this.createdAt;
    }
    @PostUpdate
    public void modifiedAt(){
        this.modifiedAt = new Date();
    }

    public void setThemes(Set<PostTheme> themes) {
        this.themes = themes;
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
