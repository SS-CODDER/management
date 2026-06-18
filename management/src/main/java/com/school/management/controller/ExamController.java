package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.entity.Exam;
import com.school.management.service.ClassRoomService;
import com.school.management.service.ExamService;
import com.school.management.service.SectionService;
import com.school.management.service.SubjectService;

@Controller
public class ExamController {

	@Autowired
	private ExamService examService;

	@Autowired
	private ClassRoomService classRoomService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private SubjectService subjectService;

	@GetMapping("/admin/exams")
	public String exams(Model model) {

		model.addAttribute("exams", examService.getAllExams());

		return "admin/exams";
	}

	@GetMapping("/admin/add-exam")
	public String addExam(Model model) {

		model.addAttribute("exam", new Exam());

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("sections", sectionService.getAllSections());

		model.addAttribute("subjects", subjectService.getAllSubjects());

		return "admin/add-exam";
	}

	@PostMapping("/admin/save-exam")
	public String saveExam(@ModelAttribute Exam exam, RedirectAttributes ra) {

		if (examService.examExists(exam)) {

			ra.addFlashAttribute("error", "Exam already scheduled");

			return "redirect:/admin/add-exam";
		}

		examService.saveExam(exam);

		ra.addFlashAttribute("success", "Exam Scheduled Successfully");

		return "redirect:/admin/exams";
	}

}
