package com.school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String examName;

	private String className;

	private String section;

	private String subject;

	private String examDate;

	private String startTime;

	private String endTime;

	private String roomNo;
}