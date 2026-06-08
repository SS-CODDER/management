package com.school.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Teacher;
import com.school.management.entity.User;


public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
	Optional<Teacher> findByUser(User user);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
}
