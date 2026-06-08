package com.school.management.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.school.management.entity.Fee;
import com.school.management.service.FeeService;

@RestController
public class FeeReceiptController {

	@Autowired
	private FeeService feeService;

	@GetMapping("/admin/receipt/{id}")
	public ResponseEntity<InputStreamResource> downloadReceipt(@PathVariable Long id) throws Exception {

		Fee fee = feeService.getFeeById(id);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document();

		PdfWriter.getInstance(document, out);

		document.open();

		document.add(new Paragraph("School Fee Receipt"));

		document.add(new Paragraph("Receipt No : " + fee.getReceiptNo()));

		document.add(new Paragraph("Student : " + fee.getStudent().getFirstName()));

		document.add(new Paragraph("Class : " + fee.getClassName()));

		document.add(new Paragraph("Month : " + fee.getFeeMonth()));

		document.add(new Paragraph("Paid Amount : " + fee.getPaidAmount()));

		document.close();

		InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

		return ResponseEntity.ok()

				.header(HttpHeaders.CONTENT_DISPOSITION,

						"attachment; filename=receipt.pdf")

				.contentType(MediaType.APPLICATION_PDF)

				.body(file);
	}
}