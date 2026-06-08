package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.ClassRoom;
import com.school.management.entity.Section;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAssignment;

public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {

	List<TeacherAssignment> findByTeacher(Teacher teacher);

//	List<Attendance> findByStudent_IdAndAttendanceDate(Long id, LocalDate now);
	boolean existsByTeacherAndClassRoomAndSection(Teacher teacher, ClassRoom classRoom, Section section);

}