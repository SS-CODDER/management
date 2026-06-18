package com.school.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.dto.DashboardDTO;

@Service
public class DashboardService {

	@Autowired
	private FeeService feeService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private ExamService examService;

	public DashboardDTO getDashboardData() {

		DashboardDTO dto = new DashboardDTO();

		List<Double> fees = new ArrayList<>();

		for (int i = 1; i <= 12; i++) {

			fees.add(feeService.getMonthCollection(i));
		}

		dto.setMonthlyFees(fees);

		List<Long> students = new ArrayList<>();

		for (int i = 1; i <= 12; i++) {

			students.add(studentService.countStudentsByMonth(i));
		}

		dto.setMonthlyStudents(students);

		dto.setPresentCount(attendanceService.presentCount());

		dto.setAbsentCount(attendanceService.absentCount());

		dto.setRecentStudents(studentService.recentStudents());

		dto.setRecentFees(feeService.recentFees());

		dto.setUpcomingExams(examService.upcomingExams());

		return dto;
	}
}