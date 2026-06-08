package com.school.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "timetable")
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    private String dayName;

    private String startTime;

    private String endTime;

    private String subject;

    private String teacherName;

    private String roomNo;
    private String section;
    private String subjectCode;
    private String teacherEmail;

    // Getter Setter
}
