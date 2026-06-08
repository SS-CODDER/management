package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

	List<Timetable> findByClassName(String className);

	List<Timetable> findByTeacherName(String teacherName);

	List<Timetable> findByClassNameAndSection(String className, String section); 
}
