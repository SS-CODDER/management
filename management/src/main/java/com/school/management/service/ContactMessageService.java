package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.ContactMessage;
import com.school.management.repository.ContactMessageRepository;

@Service
public class ContactMessageService {

	@Autowired
	private ContactMessageRepository repository;

	public ContactMessage save(ContactMessage message) {

		return repository.save(message);
	}

	public List<ContactMessage> findAll() {

		return repository.findAll();
	}

	
	public void markAsRead(Long id) {

		ContactMessage message = repository.findById(id).orElseThrow();

		message.setReadStatus(true);

		repository.save(message);
	}

	
	public void delete(Long id) {

		repository.deleteById(id);
	}

}