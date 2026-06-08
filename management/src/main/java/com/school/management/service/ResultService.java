package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Result;
import com.school.management.repository.ResultRepository;

@Service
public class ResultService {

	@Autowired
	private ResultRepository repository;

	public void saveResult(Result result) {

		repository.save(result);
	}

	public List<Result> getStudentResults(Long studentId) {

		return repository.findByStudent_Id(studentId);
	}

	public boolean resultExists(Long studentId, Long subjectId) {

		return repository.existsByStudent_IdAndSubject_Id(studentId, subjectId);
	}

	public String calculateGrade(double percentage) {

		if (percentage >= 90)
			return "A+";

		if (percentage >= 80)
			return "A";

		if (percentage >= 70)
			return "B";

		if (percentage >= 60)
			return "C";

		if (percentage >= 50)
			return "D";

		return "F";
	}

	public String calculateStatus(double percentage) {

		return percentage >= 40 ? "PASS" : "FAIL";
	}
}