package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.management.entity.Teacher;
import com.school.management.entity.User;
import com.school.management.repository.TeacherRepository;
import com.school.management.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository trRepository;

	@Autowired
	private UserRepository userRepository;

	// SAVE

	public void saveTeacher(Teacher teacher) {
		trRepository.save(teacher);
	}

	// GET BY ID

	public Teacher getTeacherById(Long id) {
		return trRepository.findById(id).orElse(null);
	}

	// DELETE

	@Transactional
	public void deleteTeacher(Long id) {

		Teacher teacher = trRepository.findById(id).orElseThrow();

		User user = teacher.getUser();

		trRepository.delete(teacher);

		if (user != null) {
			userRepository.delete(user);
		}
	}

	public long totalTeachers() {

		return trRepository.count();
	}

	public Teacher getLoggedTeacher() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		User user = userRepository.findByUsername(username).orElseThrow();

		return trRepository.findByUser(user).orElseThrow();
	}

	public List<Teacher> getAllTeachers() {
		// TODO Auto-generated method stub
		return trRepository.findAll();
	}

	public boolean existsByEmail(String email) {
		return trRepository.existsByEmail(email);
	}

	public boolean existsByPhone(String phone) {
		return trRepository.existsByPhone(phone);
	}
}
