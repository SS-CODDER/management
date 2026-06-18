package com.school.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.entity.Fee;
import com.school.management.entity.Notice;
import com.school.management.entity.Result;
import com.school.management.entity.Student;
import com.school.management.entity.Timetable;
import com.school.management.entity.User;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.UserRepository;
import com.school.management.service.AttendanceService;
import com.school.management.service.ExamService;
import com.school.management.service.FeeService;
import com.school.management.service.HomeworkService;
import com.school.management.service.NoticeService;
import com.school.management.service.ResultService;
import com.school.management.service.StudentService;
import com.school.management.service.TimetableService;

@Controller
public class StudentController {

	@Autowired
	private ResultService resultService;

	@Autowired
	private FeeService feeService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private TimetableService timetableService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ExamService examService;

	@Autowired
	private HomeworkService homeworkService;

	@GetMapping("/student/dashboard")
	public String dashboard(Model model) {

		Student student = studentService.getCurrentStudent();

		model.addAttribute("studentName", student.getFirstName() + " " + student.getLastName());

		Notice notice = noticeService.getLatestNotice();

		model.addAttribute("attendancePercentage",

				attendanceService.getAttendancePercentage(student.getId()));
		Fee fee = feeService.getStudentFee(student.getId());

		model.addAttribute("dueFee",

				fee != null ? fee.getDueAmount() : 0);

		List<Result> results = resultService.getStudentResults(student.getId());

		if (!results.isEmpty()) {

			model.addAttribute(

					"latestGrade",

					results.get(results.size() - 1).getGrade());
		}

		model.addAttribute("student", student);

		model.addAttribute("upcomingExams",
				examService.getClassExams(student.getClassName(), student.getSection()).size());

		model.addAttribute("homeworkCount",
				homeworkService.getClassHomework(student.getClassName(), student.getSection()).size());

		model.addAttribute("latestNotice",

				notice != null ? notice.getTitle() : "No Notice Available");
		return "student/dashboard";
	}

	@GetMapping("/student/attendance")
	public String attendance(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();

		User user = userRepository.findByUsername(username).orElseThrow();

		Student student = studentRepository.findByUser(user).orElseThrow();

		model.addAttribute("attendanceList", attendanceService.getStudentAttendance(student.getId()));

		double percentage = attendanceService.getAttendancePercentage(student.getId());

		model.addAttribute("percentage", percentage);

		return "student/attendance";
	}

	@GetMapping("/student/result")
	public String result(Model model) {

		Student student = studentService.getCurrentStudent();

		Long studentId = student.getId();

		List<Result> results = resultService.getStudentResults(studentId);

		if (results.isEmpty()) {

			model.addAttribute("message", "Result Not Found");

			return "student/results";
		}

		int total = results.stream().mapToInt(Result::getTotalMarks).sum();

		double totalObtained = results.stream().mapToDouble(Result::getObtainedMarks).sum();

		double totalMarks = results.stream().mapToDouble(Result::getTotalMarks).sum();

		double percentage = (totalObtained * 100) / totalMarks;

		String grade = resultService.calculateGrade(percentage);

		if (percentage >= 90) {
			grade = "A+";
		} else if (percentage >= 80) {
			grade = "A";
		} else if (percentage >= 70) {
			grade = "B";
		} else if (percentage >= 60) {
			grade = "C";
		} else {
			grade = "Fail";
		}

		model.addAttribute("results", results);
		model.addAttribute("total", total);
		model.addAttribute("percentage", percentage);
		model.addAttribute("grade", grade);
		model.addAttribute("totalObtained", totalObtained);

		model.addAttribute("totalMarks", totalMarks);

		return "student/results";
	}

	@GetMapping("/student/fees")
	public String fees(Model model) {

		Student student = studentService.getCurrentStudent();

		Long studentId = student.getId();

		model.addAttribute("fee", feeService.getStudentFee(studentId));

		return "student/fees";
	}

	@GetMapping("/admin/load-students")
	@ResponseBody
	public List<Student> loadStudents(@RequestParam String className, @RequestParam String section) {

		return studentService.findByClassAndSection(className, section);
	}

	@GetMapping("/student/notices")
	public String notices(Model model) {

		model.addAttribute("notices", noticeService.getAllNotices());

		return "student/notices";
	}

	@GetMapping("/student/timetable")
	public String timetable(Model model) {

		Student student = studentService.getCurrentStudent();

//		String className = student.getClassName();
//		model.addAttribute("timetable", timetableService.getClassTimetable(className));
//		
		List<Timetable> timetables = timetableService.getByClassAndSection(student.getClassName(),
				student.getSection());

		model.addAttribute("timetables", timetables);

		return "student/timetable";
	}

	@GetMapping("/student/exams")
	public String studentExams(Model model) {

		Student student = studentService.getCurrentStudent();

		model.addAttribute("exams",

				examService.getClassExams(student.getClassName(), student.getSection()));

		return "student/exams";
	}

	@GetMapping("/student/homeworks")
	public String homeworks(Model model) {

		Student student = studentService.getCurrentStudent();

		model.addAttribute("homeworks",

				homeworkService.getClassHomework(student.getClassName(), student.getSection()));

		return "student/homeworks";
	}

	@GetMapping("/student/profile")
	public String profile(Model model) {

		Student student = studentService.getCurrentStudent();

		model.addAttribute("student", student);

		return "student/profile";
	}

	@PostMapping("/student/update-profile")
	public String updateProfile(

			@ModelAttribute Student formStudent,

			RedirectAttributes ra) {

		Student student = studentService.getCurrentStudent();

		student.setPhone(formStudent.getPhone());

		student.setEmail(formStudent.getEmail());

		student.setAddress(formStudent.getAddress());

		studentService.saveStudent(student);

		ra.addFlashAttribute("success", "Profile Updated Successfully");

		return "redirect:/student/profile";
	}
}