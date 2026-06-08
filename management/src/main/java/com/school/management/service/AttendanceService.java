package com.school.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Attendance;
import com.school.management.repository.AttendanceRepository;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceRepository repository;

	// SAVE

	public void saveAttendance(Attendance attendance) {

		repository.save(attendance);
	}

	// GET ALL

	public List<Attendance> getAllAttendance() {

		return repository.findAll();
	}

	// STUDENT ATTENDANCE

	public List<Attendance> getStudentAttendance(Long studentId) {

		return repository.findByStudent_Id(studentId);
	}

	public List<Attendance> getAttendanceReport(String className, String section, LocalDate date) {

		return repository.findByStudent_ClassNameAndStudent_SectionAndAttendanceDate(className, section, date);
	}

	public long presentCount(Long studentId) {

		return repository.findByStudent_Id(studentId).stream().filter(a -> a.getStatus().equals("Present")).count();
	}

	public double attendancePercentage(Long studentId) {

		List<Attendance> list = repository.findByStudent_Id(studentId);

		long present = list.stream().filter(a -> a.getStatus().equals("Present")).count();

		if (list.isEmpty()) {
			return 0;
		}

		return (present * 100.0) / list.size();
	}

	public double getAttendancePercentage(Long studentId) {

		List<Attendance> list = repository.findByStudent_Id(studentId);

		if (list.isEmpty()) {
			return 0;
		}

		long present = list.stream().filter(a -> a.getStatus().equalsIgnoreCase("Present")).count();

		return (present * 100.0) / list.size();
	}
}
