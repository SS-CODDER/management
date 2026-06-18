package com.school.management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public String handleDuplicate(DataIntegrityViolationException ex, Model model) {
//
//		model.addAttribute("error", "Duplicate data found. Email, Username or Admission No already exists.");
//
//		return "error/error-page";
//	}
//
//	@ExceptionHandler(Exception.class)
//	public String handleAll(Exception ex, Model model) {
//
//		model.addAttribute("error", ex.getMessage());
//
//		return "error/error-page";
//	}
}
