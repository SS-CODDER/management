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

import com.school.management.entity.Teacher;
import com.school.management.entity.User;
import com.school.management.repository.UserRepository;
import com.school.management.service.ActivityLogService;
import com.school.management.service.ClassRoomService;
import com.school.management.service.SectionService;
import com.school.management.service.SubjectService;
import com.school.management.service.TeacherService;

@Controller
public class TeacherAdminController {

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ClassRoomService classRoomService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ActivityLogService activityLogService;

	@GetMapping("/admin/teachers")
	public String teachers(Model model) {

		model.addAttribute("teachers", teacherService.getAllTeachers());

		return "admin/teachers";
	}

	@GetMapping("/admin/add-teacher")
	public String addTeacher(Model model) {

		model.addAttribute("teacher", new Teacher());

		return "admin/add-teacher";
	}

	@PostMapping("/admin/save-teacher")
	public String saveTeacher(@ModelAttribute Teacher teacher, RedirectAttributes ra) {

		if (userRepository.existsByUsername(teacher.getEmail())) {

			ra.addFlashAttribute("error", "Teacher Email already exists!");

			return "redirect:/admin/add-teacher";
		}

		if (teacherService.existsByEmail(teacher.getEmail())) {

			ra.addFlashAttribute("error", "Teacher Email already exists!");

			return "redirect:/admin/add-teacher";
		}

		if (teacherService.existsByPhone(teacher.getPhone())) {

			ra.addFlashAttribute("error", "Teacher Phone already exists!");

			return "redirect:/admin/add-teacher";
		}

		User user = new User();

		user.setUsername(teacher.getEmail());
		user.setEmail(teacher.getEmail());
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRole("ROLE_TEACHER");

		userRepository.save(user);

		teacher.setUser(user);

		teacherService.saveTeacher(teacher);

		activityLogService.saveLog("New Teacher Added", "Admin");

		ra.addFlashAttribute("success", "Teacher Added Successfully");

		return "redirect:/admin/teachers";
	}

	@GetMapping("/admin/edit-teacher/{id}")
	public String editTeacher(@PathVariable Long id, Model model) {

		model.addAttribute("teacher", teacherService.getTeacherById(id));

		return "admin/edit-teacher";
	}

	@PostMapping("/admin/update-teacher")
	public String updateTeacher(@ModelAttribute Teacher teacher) {

		Teacher oldTeacher = teacherService.getTeacherById(teacher.getId());

		teacher.setUser(oldTeacher.getUser());

		teacherService.saveTeacher(teacher);

		return "redirect:/admin/teachers";
	}

	@GetMapping("/admin/delete-teacher/{id}")
	public String deleteTeacher(@PathVariable Long id) {

		Teacher teacher = teacherService.getTeacherById(id);

		if (teacher != null && teacher.getUser() != null) {

			userRepository.delete(teacher.getUser());
		}

		teacherService.deleteTeacher(id);

		return "redirect:/admin/teachers";
	}

	@GetMapping("/admin/assign-teacher")
	public String assignTeacher(Model model) {

		model.addAttribute("teachers", teacherService.getAllTeachers());

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("sections", sectionService.getAllSections());

		model.addAttribute("subjects", subjectService.getAllSubjects());

		return "admin/assign-teacher";
	}

	@GetMapping("/admin/assign-teacher/{id}")
	public String assignTeacher(@PathVariable Long id, Model model) {

		Teacher teacher = teacherService.getTeacherById(id);

		model.addAttribute("teacher", teacher);

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("sections", sectionService.getAllSections());

		model.addAttribute("subjects", subjectService.getAllSubjects());

		return "admin/assign-teacher";
	}

}
