package com.school.management.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    private String examName;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private Double percentage;

    private String grade;

    private String status;

    private String remarks;

    private LocalDate examDate;
    
    private String className;

    private String section;

    private Integer examYear;
}