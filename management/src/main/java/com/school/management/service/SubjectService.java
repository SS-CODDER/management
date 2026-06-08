package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Subject;
import com.school.management.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository repository;

	public Subject save(Subject subject) {
		return repository.save(subject);
	}

	public boolean existsBySubjectName(String subjectName) {

		return repository.existsBySubjectName(subjectName);
	}

	public List<Subject> getAllSubjects() {
		return repository.findAll();
	}

	public Subject getByName(String subjectName) {

		return repository.findBySubjectName(subjectName).orElse(null);
	}

	public Subject getById(Long subjectId) {

		return repository.findById(subjectId).orElse(null);
	}
}
