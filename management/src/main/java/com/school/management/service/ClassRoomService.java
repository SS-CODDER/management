package com.school.management.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.ClassRoom;
import com.school.management.repository.ClassRoomRepository;

@Service
public class ClassRoomService {

	@Autowired
	private ClassRoomRepository repository;

	public List<ClassRoom> getAllClasses() {
		return repository.findAll();
	}

	public void save(ClassRoom classroom) {
		repository.save(classroom);
	}

	public ClassRoom getById(Long classId) {

		return repository.findById(classId).orElse(null);
	}

	public boolean existsByClassName(String className) {
		
		return false;
	}

	public long countClasses() {
		// TODO Auto-generated method stub
		return repository.findAll().size();
	}
}
