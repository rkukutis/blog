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
    private String imageName;
    private byte[] bytes;
    @CreatedDate
    private Date uploadedAt;

    public PostImage(String imageName, byte[] bytes) {
        this.imageName = imageName;
        this.bytes = bytes;
    }

    @PrePersist
    public  void logDate(){
        this.uploadedAt = new Date();
        this.imageName = uploadedAt.toInstant().getEpochSecond() + "_" + this.imageName;
    }
}
