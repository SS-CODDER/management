package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

	List<Exam> findByClassNameAndSection(String className, String section);

	boolean existsByExamNameAndClassNameAndSectionAndSubject(String examName, String className, String section,
			String subject);
}