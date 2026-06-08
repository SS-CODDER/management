package com.school.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByStudent_Id(Long studentId);

	List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

	List<Attendance> findByStudent_IdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

	List<Attendance> findByStudent_ClassNameAndStudent_Section(String className, String section);

	List<Attendance> findByStudent_ClassNameAndStudent_SectionAndAttendanceDate(String className, String section,
			LocalDate attendanceDate);

	long countByStudent_IdAndStatus(Long studentId, String status);

	long countByStudent_Id(Long studentId);
}