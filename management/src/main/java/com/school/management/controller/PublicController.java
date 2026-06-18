package com.school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.management.entity.ContactMessage;
import com.school.management.service.ContactMessageService;

@Controller
public class PublicController {

	@Autowired
	private ContactMessageService contactMessageService;

	@GetMapping("/")
	public String home() {
		return "public/index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "public/login";
	}

	@GetMapping("/contact")
	public String contact() {

		return "public/contact";

	}

	@PostMapping("/contact/save")
	public String saveContactMessage(ContactMessage contactMessage, RedirectAttributes redirectAttributes) {

		contactMessageService.save(contactMessage);

		redirectAttributes.addFlashAttribute("success", "Message sent successfully!");

		return "redirect:/contact";
	}

}
