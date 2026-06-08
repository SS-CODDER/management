package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

	List<Result> findByStudent_Id(Long studentId);

	boolean existsByStudent_IdAndSubject_Id(Long studentId, Long subjectId);

	List<Result> findByStudent_ClassNameAndStudent_Section(String className, String section);
}