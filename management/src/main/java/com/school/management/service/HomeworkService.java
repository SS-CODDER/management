package com.school.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.entity.Homework;
import com.school.management.repository.HomeworkRepository;

@Service
public class HomeworkService {

	private final HomeworkRepository repository;

	public HomeworkService(HomeworkRepository repository) {

		this.repository = repository;
	}

	public void save(Homework homework) {

		repository.save(homework);
	}

	public List<Homework> getAll() {

		return repository.findAll();
	}

	public List<Homework> getClassHomework(String className, String section) {

		return repository.findByClassNameAndSection(className, section);
	}

	public void delete(Long id) {

		repository.deleteById(id);
	}
}