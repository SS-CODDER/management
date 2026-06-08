package com.school.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.entity.Exam;
import com.school.management.repository.ExamRepository;

@Service
public class ExamService {

	private final ExamRepository repository;

	public ExamService(ExamRepository repository) {

		this.repository = repository;
	}

	public void saveExam(Exam exam) {

		repository.save(exam);
	}

	public List<Exam> getAllExams() {

		return repository.findAll();
	}

	public List<Exam> getClassExams(String className, String section) {

		return repository.findByClassNameAndSection(className, section);
	}

	public void deleteExam(Long id) {

		repository.deleteById(id);
	}

	public boolean examExists(Exam exam) {

		return repository.existsByExamNameAndClassNameAndSectionAndSubject(exam.getExamName(), exam.getClassName(),
				exam.getSection(), exam.getSubject());
	}
}