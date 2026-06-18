package com.school.management.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String admissionNo;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String phone;

	private String firstName;

	private String lastName;

	private String gender;

	private String address;

	private LocalDate dob;

	private String className;

	private String section;

	// NEW FIELDS

	private String fatherName;

	private String motherName;

	private String guardianPhone;

	private String bloodGroup;

	private String profileImage;
	
	private LocalDate admissionDate;
	
	@Transient
	private String feeStatus;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;
}