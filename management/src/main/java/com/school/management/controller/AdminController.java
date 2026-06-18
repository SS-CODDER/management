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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.dto.DashboardDTO;
import com.school.management.entity.Attendance;
import com.school.management.entity.ClassRoom;
import com.school.management.entity.Section;
import com.school.management.entity.Subject;
import com.school.management.entity.Teacher;
import com.school.management.entity.TeacherAssignment;
import com.school.management.entity.Timetable;
import com.school.management.repository.TeacherAssignmentRepository;
import com.school.management.service.ActivityLogService;
import com.school.management.service.AttendanceService;
import com.school.management.service.ClassRoomService;
import com.school.management.service.ContactMessageService;
import com.school.management.service.DashboardService;
import com.school.management.service.ExamService;
import com.school.management.service.FeeService;
import com.school.management.service.HolidayService;
import com.school.management.service.HomeworkService;
import com.school.management.service.SectionService;
import com.school.management.service.StudentService;
import com.school.management.service.SubjectService;
import com.school.management.service.TeacherAssignmentService;
import com.school.management.service.TeacherService;
import com.school.management.service.TimetableService;

@Controller
public class AdminController {

	@Autowired
	private StudentService studentService;

//	@Autowired
//	private NoticeService noticeService;

	@Autowired
	private TimetableService timetableService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private TeacherAssignmentRepository teacherAssignmentRepository;

	@Autowired
	private TeacherAssignmentService teacherAssignmentService;

	@Autowired
	private FeeService feeService;

	@Autowired
	private ClassRoomService classRoomService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private ExamService examService;

	@Autowired
	private HomeworkService homeworkService;

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private ContactMessageService contactMessageService;

	@Autowired
	private ActivityLogService activityLogService;
	
	@Autowired
	private HolidayService holidayService;

	@GetMapping("/admin/dashboard")
	public String adminDashboard(Model model) {

		model.addAttribute("totalStudents", studentService.totalStudents());

		model.addAttribute("totalTeachers", teacherService.totalTeachers());

		model.addAttribute("totalClasses", classRoomService.getAllClasses().size());

		model.addAttribute("totalSubjects", subjectService.getAllSubjects().size());

		model.addAttribute("totalSections", sectionService.getAllSections().size());

		model.addAttribute("totalAssignments", teacherAssignmentRepository.findAll().size());

		model.addAttribute("totalFees", feeService.totalCollection());

		model.addAttribute("totalExams", examService.getAllExams().size());

		model.addAttribute("totalHomework", homeworkService.getAll().size());

		model.addAttribute("monthlyFees", feeService.getTotalCollectedFees());

		model.addAttribute("pendingFees", feeService.getTotalPendingFees());

		model.addAttribute("presentCount", attendanceService.presentCount());

		model.addAttribute("absentCount", attendanceService.absentCount());

		return "admin/dashboard";
	}

	@PostMapping("/admin/save-assignment")
	public String saveAssignment(

			@RequestParam Long teacherId,

			@RequestParam Long classId,

			@RequestParam Long sectionId,

			@RequestParam Long subjectId

	) {

		Teacher teacher = teacherService.getTeacherById(teacherId);

		ClassRoom classroom = classRoomService.getById(classId);

		Section section = sectionService.getById(sectionId);

		Subject subject = subjectService.getById(subjectId);

		TeacherAssignment assignment = new TeacherAssignment();

		assignment.setTeacher(teacher);

		assignment.setClassRoom(classroom);

		assignment.setSection(section);

		assignment.setSubject(subject);

		if (teacherAssignmentService.alreadyAssigned(teacher, classroom, section)) {

			return "redirect:/admin/dashboard";
		}

		teacherAssignmentRepository.save(assignment);

		return "redirect:/admin/dashboard";
	}

	@GetMapping("/admin/add-timetable")
	public String timetablePage() {

		return "admin/add-timetable";
	}

	@PostMapping("/admin/save-timetable")
	public String saveTimetable(

			@RequestParam String className, @RequestParam String dayName, @RequestParam String startTime,
			@RequestParam String endTime, @RequestParam String subject, @RequestParam String teacherName,
			@RequestParam String roomNo, @RequestParam String section

	) {

		Timetable timetable = new Timetable();

		timetable.setClassName(className);
		timetable.setDayName(dayName);
		timetable.setStartTime(startTime);
		timetable.setEndTime(endTime);
		timetable.setSubject(subject);
		timetable.setTeacherName(teacherName);
		timetable.setRoomNo(roomNo);
		timetable.setSection(section);

		timetableService.save(timetable);

		return "redirect:/admin/dashboard";
	}

	@GetMapping("/admin/classes")
	public String classes(Model model) {

		model.addAttribute("classes", classRoomService.getAllClasses());

		model.addAttribute("classRoom", new ClassRoom());

		return "admin/classes";
	}

	@PostMapping("/admin/save-class")
	public String saveClass(@ModelAttribute ClassRoom classRoom, RedirectAttributes ra) {

		if (classRoomService.existsByClassName(classRoom.getClassName())) {

			ra.addFlashAttribute("error", "Class already exists");

			return "redirect:/admin/classes";
		}

		classRoomService.save(classRoom);

		ra.addFlashAttribute("success", "Class Added Successfully");

		return "redirect:/admin/classes";
	}

	@GetMapping("/admin/sections")
	public String sections(Model model) {

		model.addAttribute("sections", sectionService.getAllSections());

		model.addAttribute("section", new Section());

		model.addAttribute("classes", classRoomService.getAllClasses());

		return "admin/sections";
	}

	@PostMapping("/admin/save-section")
	public String saveSection(

			@ModelAttribute Section section,

			RedirectAttributes ra) {

		if (sectionService.existsBySectionName(section.getSectionName())) {

			ra.addFlashAttribute("error", "Section Already Exists");

			return "redirect:/admin/sections";
		}

		sectionService.save(section);

		ra.addFlashAttribute("success", "Section Added Successfully");

		return "redirect:/admin/sections";
	}

	@GetMapping("/admin/subjects")
	public String subjects(Model model) {

		model.addAttribute("subjects", subjectService.getAllSubjects());

		model.addAttribute("subject", new Subject());

		return "admin/subjects";
	}

	@PostMapping("/admin/save-subject")
	public String saveSubject(

			@ModelAttribute Subject subject,

			RedirectAttributes ra) {

		if (subjectService.existsBySubjectName(subject.getSubjectName())) {

			ra.addFlashAttribute("error", "Subject Already Exists");

			return "redirect:/admin/subjects";
		}

		subjectService.save(subject);

		ra.addFlashAttribute("success", "Subject Added Successfully");

		return "redirect:/admin/subjects";
	}

	@GetMapping("/admin/attendance-report")
	public String attendanceReportPage() {

		return "admin/attendance-report";
	}

	@PostMapping("/admin/attendance-report")
	public String attendanceReport(

			@RequestParam String className, @RequestParam String section, @RequestParam String date,

			Model model

	) {

		LocalDate attendanceDate = LocalDate.parse(date);

		List<Attendance> report = attendanceService.getAttendanceReport(className, section, attendanceDate);

		model.addAttribute("report", report);

		return "admin/attendance-report";
	}

	@GetMapping("/admin/fee-report")
	public String feeReportPage() {

		return "admin/fee-report";
	}

	@PostMapping("/admin/fee-report")
	public String feeReport(

			@RequestParam String startDate,

			@RequestParam String endDate,

			Model model) {

		model.addAttribute(

				"fees",

				feeService.getFeesBetweenDates(

						LocalDate.parse(startDate),

						LocalDate.parse(endDate)));

		return "admin/fee-report";
	}

	@GetMapping("/admin/dashboard-data")
	@ResponseBody
	public DashboardDTO dashboardData() {

		return dashboardService.getDashboardData();
	}

	@GetMapping("/admin/messages")
	public String contactMessages(Model model) {

		model.addAttribute("messages", contactMessageService.findAll());

		return "admin/contact-messages";
	}

	@GetMapping("/admin/message/read/{id}")
	public String markAsRead(@PathVariable Long id) {

		contactMessageService.markAsRead(id);

		return "redirect:/admin/messages";
	}

	@GetMapping("/admin/message/delete/{id}")
	public String deleteMessage(@PathVariable Long id) {

		contactMessageService.delete(id);

		return "redirect:/admin/messages";
	}

	@GetMapping("/admin/reports")
	public String reports(Model model) {

		model.addAttribute("totalStudents", studentService.countStudents());

		model.addAttribute("totalTeachers", teacherService.countTeachers());

		model.addAttribute("totalClasses", classRoomService.countClasses());

		model.addAttribute("totalSubjects", subjectService.countSubjects());

		model.addAttribute("totalExams", examService.getAllExams().size());

		model.addAttribute("presentCount", attendanceService.presentCount());

		model.addAttribute("absentCount", attendanceService.absentCount());

		model.addAttribute("totalCollection", feeService.totalCollection());

		model.addAttribute("pendingFees", feeService.getTotalPendingFees());

		return "admin/reports";
	}

	@GetMapping("/admin/activity-log")
	public String activityLog(Model model) {

		model.addAttribute("logs", activityLogService.latestLogs());

		return "admin/activity-log";
	}

	@GetMapping("/admin/holidays")
	public String holidays(Model model) {

		model.addAttribute("holidays", holidayService.getUpcomingHolidays());

		return "admin/holidays";
	}
}