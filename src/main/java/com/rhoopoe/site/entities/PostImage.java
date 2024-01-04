package com.rhoopoe.site.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Setter
    private String imageName;
    @Setter
    private String path;
    @CreatedDate
    private Date uploadedAt;


    @PrePersist
    public  void logDate(){
        this.uploadedAt = new Date();
    }
}
