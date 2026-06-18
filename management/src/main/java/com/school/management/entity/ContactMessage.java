package com.school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Column(length = 5000)
    private String message;

    private LocalDateTime createdAt;

    private boolean readStatus = false;

    public ContactMessage() {
        this.createdAt = LocalDateTime.now();
    }

    // getters setters
}