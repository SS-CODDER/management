package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Homework;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

	List<Homework> findByClassNameAndSection(String className, String section);
}