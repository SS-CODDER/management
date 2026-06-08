package com.school.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.entity.Fee;
import com.school.management.repository.FeeRepository;

@Service
public class FeeService {

	private final FeeRepository repository;

	public FeeService(FeeRepository repository) {
		this.repository = repository;
	}

	public void saveFee(Fee fee) {

		double due = fee.getTotalFee() - fee.getPaidAmount();

		fee.setDueAmount(due);

		fee.setPaymentDate(LocalDate.now());

		fee.setReceiptNo("REC-" + System.currentTimeMillis());

		if (due <= 0) {

			fee.setStatus("PAID");

		} else if (fee.getPaidAmount() > 0) {

			fee.setStatus("PARTIAL");

		} else {

			fee.setStatus("PENDING");
		}

		repository.save(fee);
	}

	public Fee getStudentFee(Long studentId) {

		return repository.findByStudent_Id(studentId).orElse(null);
	}

	public List<Fee> getAllFees() {
		return repository.findAll();
	}

	public Fee getFeeById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public void deleteFee(Long id) {
		repository.deleteById(id);
	}

	public List<Fee> getStudentFees(Long studentId) {

		return repository.findByStudent_IdOrderByPaymentDateDesc(studentId);
	}

	public boolean alreadyPaid(Long studentId, String month, Integer year) {

		return repository.existsByStudent_IdAndFeeMonthAndFeeYear(studentId, month, year);
	}

	public String getCurrentMonthStatus(Long studentId) {

		return repository.findTopByStudent_IdOrderByIdDesc(studentId).map(Fee::getStatus).orElse("PENDING");
	}

	public double totalCollection() {

		return repository.findAll().stream()

				.mapToDouble(Fee::getPaidAmount)

				.sum();
	}

	public long totalPaidFees() {

		return repository.findAll().stream()

				.filter(f -> "PAID".equals(f.getStatus()))

				.count();
	}

	public List<Fee> getFeesBetweenDates(LocalDate start, LocalDate end) {

		return repository.findByPaymentDateBetween(start, end);
	}

}
