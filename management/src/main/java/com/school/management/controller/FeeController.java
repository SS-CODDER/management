package com.school.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.entity.Fee;
import com.school.management.entity.Student;
import com.school.management.service.ClassRoomService;
import com.school.management.service.FeeService;
import com.school.management.service.SectionService;
import com.school.management.service.StudentService;

@Controller
public class FeeController {

	@Autowired
	private FeeService feeService;
	@Autowired
	private ClassRoomService classRoomService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private StudentService studentService;

	@GetMapping("/admin/fees")
	public String fees(Model model) {

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("sections", sectionService.getAllSections());

		return "admin/fees";
	}

	@GetMapping("/admin/fees/students")
	public String feeStudents(

			@RequestParam String className,

			@RequestParam String section,

			Model model) {

		List<Student> students = studentService.findByClassAndSection(className, section);

		model.addAttribute("students", students);

		model.addAttribute("className", className);

		model.addAttribute("section", section);

		return "admin/fee-students";
	}

	@GetMapping("/admin/add-fee")
	public String addFee(Model model) {

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("sections", sectionService.getAllSections());

		model.addAttribute("fee", new Fee());

		return "admin/add-fee";
	}

	@GetMapping("/admin/pay-fee/{id}")
	public String payFee(@PathVariable Long id, Model model) {

		Student student = studentService.getStudentById(id);

		Fee fee = new Fee();

		fee.setFeeMonth(LocalDate.now().getMonth().name());

		fee.setFeeYear(LocalDate.now().getYear());

		model.addAttribute("student", student);

		model.addAttribute("fee", fee);

		return "admin/pay-fee";
	}

	@PostMapping("/admin/save-fee")
	public String saveFee(

			@RequestParam Long studentId,

			@ModelAttribute Fee fee,

			RedirectAttributes ra) {

		Student student = studentService.getStudentById(studentId);

		if (feeService.alreadyPaid(studentId, fee.getFeeMonth(), fee.getFeeYear())) {

			ra.addFlashAttribute("error", "Fee already submitted for " + fee.getFeeMonth());

			return "redirect:/admin/pay-fee/" + studentId;
		}

		fee.setStudent(student);

		fee.setClassName(student.getClassName());

		fee.setSection(student.getSection());

		feeService.saveFee(fee);

		ra.addFlashAttribute("success", "Fee Submitted Successfully");

		return "redirect:/admin/fees";
	}

	
	
}
