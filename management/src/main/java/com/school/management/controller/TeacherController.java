package com.school.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.school.management.entity.Attendance;
import com.school.management.entity.Homework;
import com.school.management.entity.Result;
import com.school.management.entity.Student;
import com.school.management.entity.Subject;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAssignment;
import com.school.management.repository.AttendanceRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.service.AttendanceService;
import com.school.management.service.ExamService;
import com.school.management.service.HomeworkService;
import com.school.management.service.NoticeService;
import com.school.management.service.ResultService;
import com.school.management.service.StudentService;
import com.school.management.service.SubjectService;
import com.school.management.service.TeacherAssignmentService;
import com.school.management.service.TeacherService;
import com.school.management.service.TimetableService;

@Controller
public class TeacherController {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private ResultService resultService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private TimetableService timetableService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	private ExamService examService;

	@Autowired
	private TeacherAssignmentService teacherAssignmentService;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private HomeworkService homeworkService;

	@GetMapping("/teacher/dashboard")
	public String dashboard(Model model) {

		Teacher teacher = teacherService.getLoggedTeacher();

		model.addAttribute("teacherName", teacher.getTeacherName());

		List<TeacherAssignment> assignments = teacherAssignmentService.getTeacherAssignments(teacher);

		model.addAttribute("assignedClasses", assignments.size());

		model.addAttribute("studentCount", studentRepository.count());

		model.addAttribute("todayAttendance", attendanceRepository.count());

		return "teacher/dashboard";
	}

	@GetMapping("/teacher/result-form")
	public String resultForm(Model model) {

		model.addAttribute("students", studentService.getAllStudents());

		model.addAttribute("subjects", subjectService.getAllSubjects());

		return "teacher/result-form";
	}

	@PostMapping("/teacher/save-result")
	public String saveResult(

			@RequestParam Long studentId,

			@RequestParam String subjectName,

			@RequestParam Integer obtainedMarks,

			@RequestParam Integer totalMarks,

			@RequestParam String examName,

			@RequestParam(required = false) String remarks) {

		Student student = studentService.getStudentById(studentId);

		Subject subject = subjectService.getByName(subjectName);

		double percentage = (obtainedMarks * 100.0) / totalMarks;

		Result result = new Result();

		result.setStudent(student);

		result.setSubject(subject);

		result.setExamName(examName);

		result.setTotalMarks(totalMarks);

		result.setObtainedMarks(obtainedMarks);

		result.setPercentage(percentage);

		result.setGrade(resultService.calculateGrade(percentage));

		result.setStatus(resultService.calculateStatus(percentage));

		result.setRemarks(remarks);

		resultService.saveResult(result);

		return "redirect:/teacher/result-form";
	}

	@GetMapping("/teacher/attendance")
	public String attendance(Model model) {

		Teacher teacher = teacherService.getLoggedTeacher();

		List<TeacherAssignment> assignments = teacherAssignmentService.getTeacherAssignments(teacher);

		if (assignments.isEmpty()) {

			model.addAttribute("students", List.of());

			return "teacher/attendance";
		}
		List<Student> students = studentService.findByClassAndSection(teacher.getAssignedClass(),
				teacher.getAssignedSection());

		// TeacherAssignment assignment = assignments.get(0);

//		List<Student> students = studentService.findByClassAndSection(
//
//				assignment.getClassRoom().getClassName(),
//
//				assignment.getSection().getSectionName());

		model.addAttribute("students", students);

		return "teacher/attendance";
	}

	@PostMapping("/teacher/save-attendance")
	public String saveAttendance(

			@RequestParam List<Long> studentIds, @RequestParam List<String> statuses

	) {

		for (int i = 0; i < studentIds.size(); i++) {

			Student student = studentService.getStudentById(studentIds.get(i));

			List<Attendance> existing = attendanceRepository.findByStudent_IdAndAttendanceDate(student.getId(),
					LocalDate.now());

			// Agar aaj ki attendance already lagi hui hai
			if (!existing.isEmpty()) {
				continue;
			}

			Attendance attendance = new Attendance();

			attendance.setStudent(student);

			attendance.setClassName(student.getClassName());

			attendance.setSection(student.getSection());

			attendance.setAttendanceDate(LocalDate.now());

			attendance.setStatus(statuses.get(i));

			attendanceService.saveAttendance(attendance);
		}

		return "redirect:/teacher/attendance";
	}

	@GetMapping("/teacher/notices")
	public String notices(Model model) {

		model.addAttribute("notices", noticeService.getAllNotices());

		return "student/notices";
	}

	@GetMapping("/teacher/timetable")
	public String timetable(Model model) {

		Teacher teacher = teacherService.getLoggedTeacher();

		String teacherName = teacher.getTeacherName();

		model.addAttribute("timetable", timetableService.getTeacherTimetable(teacherName));

		return "teacher/timetable";
	}

	@GetMapping("/teacher/exams")
	public String teacherExams(Model model) {

		model.addAttribute("exams", examService.getAllExams());

		return "teacher/exams";
	}

	@GetMapping("/teacher/add-homework")
	public String addHomework(Model model) {

		model.addAttribute("homework", new Homework());

		return "teacher/add-homework";
	}

	@PostMapping("/teacher/save-homework")
	public String saveHomework(

			@ModelAttribute Homework homework) {

		Teacher teacher = teacherService.getLoggedTeacher();

		homework.setTeacher(teacher);

		homework.setAssignedDate(LocalDate.now());

		homeworkService.save(homework);

		return "redirect:/teacher/homeworks";
	}

	@GetMapping("/teacher/homeworks")
	public String homeworks(Model model) {

		model.addAttribute("homeworks", homeworkService.getAll());

		return "teacher/homeworks";
	}

	@GetMapping("/teacher/profile")
	public String profile(Model model) {

		model.addAttribute(

				"teacher",

				teacherService.getLoggedTeacher());

		return "teacher/profile";
	}
}