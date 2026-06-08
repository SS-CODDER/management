package com.school.management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	private String role;

	private boolean enabled = true;

	private LocalDateTime createdAt = LocalDateTime.now();

	public User() {
	}

	public User(String username, String password, String role) {

		this.username = username;
		this.password = password;
		this.role = role;
	}

	// getters setters
}