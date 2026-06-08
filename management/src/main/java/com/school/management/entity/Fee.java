package com.school.management.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fees", uniqueConstraints = { @UniqueConstraint(columnNames = { "student_id", "feeMonth", "feeYear" }) })
@Data
public class Fee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	private String className;

	private String section;

	private Double totalFee;

	private Double paidAmount;

	private Double dueAmount;

	private Double lateFee;

	private String status;

	private LocalDate paymentDate;

	private String receiptNo;

	private String paymentMethod;

	private String transactionId;

	private String collectedBy;

	private String remarks;

	private String feeMonth;

	private Integer feeYear;
}