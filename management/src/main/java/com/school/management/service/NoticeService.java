package com.school.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.entity.Notice;
import com.school.management.repository.NoticeRepository;

@Service
public class NoticeService {

	private final NoticeRepository repository;

	public NoticeService(NoticeRepository repository) {
		this.repository = repository;
	}

	public void saveNotice(Notice notice) {

		notice.setPublishDate(LocalDate.now());

		repository.save(notice);
	}

	public List<Notice> getAllNotices() {

		return repository.findAll();
	}

	public Notice getLatestNotice() {

		return repository.findTopByOrderByPublishDateDesc().orElse(null);
	}

	public List<Notice> getActiveNotices() {

		return repository.findAll().stream()

				.filter(n -> n.getExpiryDate() == null ||

						!n.getExpiryDate().isBefore(LocalDate.now()))

				.toList();
	}
	
	public void deleteNotice(Long id){

	    repository.deleteById(id);

	}
}
