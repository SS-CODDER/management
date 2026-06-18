package com.school.management.dto;

import java.util.List;

import com.school.management.entity.Exam;
import com.school.management.entity.Fee;
import com.school.management.entity.Student;

import lombok.Data;

@Data
public class DashboardDTO {

	private List<Double> monthlyFees;

	private List<Long> monthlyStudents;

	private Long presentCount;

	private Long absentCount;
	
	private List<Student> recentStudents;

	private List<Fee> recentFees;

	private List<Exam> upcomingExams;
}