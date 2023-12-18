package com.rhoopoe.site.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @NonNull
    @Column(name = "name")
    private String senderName;

    @NonNull
    @Column(name = "email")
    private String senderEmail;

    @NonNull
    @Column(columnDefinition = "message")
    private String messageBody;
    @Column(name = "sent_at")
    private Date sentAt;

    @PrePersist
    public void logSentAt(){
        this.sentAt = new Date();
    }

}
