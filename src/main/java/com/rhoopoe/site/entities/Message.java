package com.rhoopoe.site.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Setter
    @NonNull
    @Column(name = "name")
    private String senderName;

    @Setter
    @NonNull
    @Column(name = "email")
    private String senderEmail;

    @Setter
    @NonNull
    @Column(name = "message")
    private String messageBody;

    @Column(name = "sent_at")
    private Date sentAt;

    @PrePersist
    public void logSentAt(){
        this.sentAt = new Date();
    }

}
