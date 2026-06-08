package com.school.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Fee;
import com.school.management.entity.Student;

public interface FeeRepository extends JpaRepository<Fee, Long> {

	Optional<Fee> findByStudent_Id(Long studentId);

	List<Fee> findByStudent(Student student);

	List<Fee> findByStudent_IdOrderByPaymentDateDesc(Long studentId);

	boolean existsByStudent_IdAndFeeMonthAndFeeYear(Long studentId, String feeMonth, Integer feeYear);

	Optional<Fee> findTopByStudent_IdOrderByIdDesc(Long studentId);

	List<Fee> findByPaymentDateBetween(LocalDate start, LocalDate end);

}