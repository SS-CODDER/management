package com.school.management.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String phone;

	private String teacherName;

	private String subject;

	private String assignedClass;

	private String assignedSection;

	// NEW

	private String qualification;

	private Integer experience;

	private Double salary;

	private LocalDate joiningDate;

	private String address;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;
}