package com.school.management.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.school.management.entity.Notice;
import com.school.management.service.NoticeService;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@GetMapping("/admin/add-notice")
	public String noticePage() {

		return "admin/add-notice";
	}

	@PostMapping("/admin/save-notice")
	public String saveNotice(

			@RequestParam String title,

			@RequestParam String description,

			@RequestParam String noticeType,

			@RequestParam String expiryDate) {

		Notice notice = new Notice();

		notice.setTitle(title);

		notice.setDescription(description);

		notice.setNoticeType(noticeType);

		notice.setPublishDate(LocalDate.now());

		notice.setExpiryDate(LocalDate.parse(expiryDate));

		noticeService.saveNotice(notice);

		return "redirect:/admin/dashboard";
	}

	@GetMapping("/admin/notices")
	public String notices(Model model) {

		model.addAttribute("notices", noticeService.getAllNotices());

		return "admin/notices";
	}

	@GetMapping("/admin/delete-notice/{id}")
	public String deleteNotice(@PathVariable Long id) {

		noticeService.deleteNotice(id);

		return "redirect:/admin/notices";
	}
}
