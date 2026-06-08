package com.school.management.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 3000)
    private String description;

    private String className;

    private String section;

    private String subject;

    private LocalDate assignedDate;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}