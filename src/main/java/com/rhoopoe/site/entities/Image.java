package com.rhoopoe.site.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String imageName;
    private byte[] data;
    @CreatedDate
    private Date uploadedAt;

    public Image(String imageName, byte[] data) {
        this.imageName = imageName;
        this.data = data;
    }

    @PrePersist
    public  void logDate(){
        this.uploadedAt = new Date();
        this.imageName = uploadedAt.toInstant().getEpochSecond() + "_" + this.imageName;
    }
}
