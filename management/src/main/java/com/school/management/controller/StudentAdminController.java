package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.entity.Student;
import com.school.management.entity.User;
import com.school.management.repository.UserRepository;
import com.school.management.service.ActivityLogService;
import com.school.management.service.StudentService;

@Controller
public class StudentAdminController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ActivityLogService activityLogService;

	// SHOW STUDENTS

	@GetMapping("/admin/students")
	public String students(Model model) {

		model.addAttribute("students", studentService.getAllStudents());

		return "admin/students";
	}

	// ADD FORM

	@GetMapping("/admin/add-student")
	public String addStudent(Model model) {

		model.addAttribute("student", new Student());

		return "admin/add-student";
	}

	// SAVE

	@PostMapping("/admin/save-student")
	public String saveStudent(@ModelAttribute Student student, RedirectAttributes ra) {

		if (userRepository.existsByUsername(student.getAdmissionNo())) {

			ra.addFlashAttribute("error", "Admission Number already exists!");

			return "redirect:/admin/add-student";
		}

		if (studentService.existsByAdmissionNo(student.getAdmissionNo())) {

			ra.addFlashAttribute("error", "Admission Number already exists!");

			return "redirect:/admin/add-student";
		}

		if (studentService.existsByEmail(student.getEmail())) {

			ra.addFlashAttribute("error", "Email already exists!");

			return "redirect:/admin/add-student";
		}

		if (studentService.existsByPhone(student.getPhone())) {

			ra.addFlashAttribute("error", "Phone already exists!");

			return "redirect:/admin/add-student";
		}

		User user = new User();

		user.setUsername(student.getAdmissionNo());
		user.setEmail(student.getEmail());
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRole("ROLE_STUDENT");

		userRepository.save(user);

		student.setUser(user);

		studentService.saveStudent(student);
		activityLogService.saveLog("New Student Added", "Admin");

		ra.addFlashAttribute("success", "Student Added Successfully");

		return "redirect:/admin/students";
	}

	// EDIT

	@GetMapping("/admin/edit-student/{id}")
	public String editStudent(@PathVariable Long id, Model model) {

		model.addAttribute("student", studentService.getStudentById(id));

		return "admin/edit-student";
	}

	// DELETE

	@GetMapping("/admin/delete-student/{id}")
	public String deleteStudent(@PathVariable Long id) {

		Student student = studentService.getStudentById(id);

		if (student != null && student.getUser() != null) {
			userRepository.delete(student.getUser());
		}

		studentService.deleteStudent(id);

		return "redirect:/admin/students";
	}

}
