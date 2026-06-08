package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.ClassRoom;
import com.school.management.entity.Section;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAssignment;
import com.school.management.repository.TeacherAssignmentRepository;

@Service
public class TeacherAssignmentService {

	@Autowired
	private TeacherAssignmentRepository repository;

	public void save(TeacherAssignment assignment) {
		repository.save(assignment);
	}

	public List<TeacherAssignment> getTeacherAssignments(Teacher teacher) {
		return repository.findByTeacher(teacher);
	}

	public List<TeacherAssignment> getAll() {
		return repository.findAll();
	}

	public boolean alreadyAssigned(

			Teacher teacher,

			ClassRoom classRoom,

			Section section) {

		return repository.existsByTeacherAndClassRoomAndSection(teacher, classRoom, section);
	}
}