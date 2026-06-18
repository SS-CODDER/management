package com.school.management.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.management.entity.Student;
import com.school.management.entity.User;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	@Autowired
	private UserRepository userRepository;

	// GET ALL

	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	// SAVE

	public void saveStudent(Student student) {
		repository.save(student);
	}

	// GET BY ID

	public Student getStudentById(Long id) {
		return repository.findById(id).orElse(null);
	}

	// DELETE

	@Transactional
	public void deleteStudent(Long id) {

		Student student = repository.findById(id).orElse(null);

		if (student != null) {

			repository.delete(student);
		}
	}

	public long totalStudents() {

		return repository.count();
	}

//    public Student findByUsername(String username){
//        return repository.findByUsername(username);
//    }

	public Student getCurrentStudent() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		User user = userRepository.findByUsername(username).orElseThrow();

		return repository.findByUser(user).orElseThrow();
	}

//	public List<Student> getStudentsByClassAndSection(String className, String section) {
//
//		return repository.findByClassNameAndSection(className, section);
//	}

	public List<Student> findByClassAndSection(String className, String section) {

		return repository.findByClassNameAndSection(className, section);
	}

	public boolean existsByAdmissionNo(String admissionNo) {
		return repository.existsByAdmissionNo(admissionNo);
	}

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	public boolean existsByPhone(String phone) {
		return repository.existsByPhone(phone);
	}

	public Long countStudentsByMonth(int month) {

		return repository.countByMonth(month);
	}

	public List<Student> recentStudents() {

		return repository.findTop5ByOrderByIdDesc();
	}

	public long countStudents() {
		// TODO Auto-generated method stub
		return repository.findAll().size();
	}
}