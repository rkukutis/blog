package com.rhoopoe.site.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String colorHex;

    @CreatedDate
    private Date createdAt;

    @ManyToMany(mappedBy = "themes")
    @JsonIgnore
    private Set<Post> posts;

    @PrePersist
    protected void logData() {
        this.createdAt = new Date();
    }
}
