package com.school.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.management.entity.Student;
import com.school.management.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByFirstNameContainingIgnoreCase(String keyword);

	Optional<Student> findByUser(User user);

	List<Student> findByClassNameAndSection(String className, String section);

	boolean existsByAdmissionNo(String admissionNo);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

	// boolean existsBySubjectName(String subjectName);

	@Query("SELECT COUNT(s) FROM Student s WHERE MONTH(s.admissionDate)=?1 AND YEAR(s.admissionDate)=YEAR(CURRENT_DATE)")
	Long countByMonth(int month);

	List<Student> findTop5ByOrderByIdDesc();
}