package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Holiday;
import com.school.management.repository.HolidayRepository;

@Service
public class HolidayService {

	@Autowired
	private HolidayRepository repository;

	public Holiday save(Holiday holiday) {

		return repository.save(holiday);
	}

	public List<Holiday> getUpcomingHolidays() {

		return repository.findTop10ByOrderByHolidayDateAsc();
	}
}