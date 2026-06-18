package com.school.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.management.entity.Fee;
import com.school.management.entity.Student;

public interface FeeRepository extends JpaRepository<Fee, Long> {

	Optional<Fee> findByStudent_Id(Long studentId);

	List<Fee> findByStudent(Student student);

	List<Fee> findByStudent_IdOrderByPaymentDateDesc(Long studentId);

	boolean existsByStudent_IdAndFeeMonthAndFeeYear(Long studentId, String feeMonth, Integer feeYear);

	Optional<Fee> findTopByStudent_IdOrderByIdDesc(Long studentId);

	List<Fee> findByPaymentDateBetween(LocalDate start, LocalDate end);

	@Query("SELECT SUM(f.paidAmount) FROM Fee f")
	Double sumCollectedFees();

	@Query("SELECT SUM(f.dueAmount) FROM Fee f")
	Double sumPendingFees();

	List<Fee> findByFeeMonth(String feeMonth);

	@Query("SELECT COALESCE(SUM(f.paidAmount),0) FROM Fee f WHERE UPPER(f.feeMonth)=UPPER(?1)")
	Double getMonthCollection(String month);

	@Query("SELECT COALESCE(SUM(f.paidAmount),0) FROM Fee f WHERE MONTH(f.paymentDate)=?1")
	Double getMonthCollection1(int month);

	List<Fee> findTop5ByOrderByPaymentDateDesc();

	@Query("SELECT f FROM Fee f	WHERE f.dueAmount > 0 ORDER BY f.dueAmount DESC")
	List<Fee> findDefaulters();
}