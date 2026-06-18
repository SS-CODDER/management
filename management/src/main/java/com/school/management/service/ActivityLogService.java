package com.school.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.school.management.entity.ActivityLog;
import com.school.management.repository.ActivityLogRepository;

@Service
public class ActivityLogService {

	private final ActivityLogRepository repository;

	public ActivityLogService(ActivityLogRepository repository) {

		this.repository = repository;
	}

	public void saveLog(String activity, String user) {

		ActivityLog log = new ActivityLog();

		log.setActivity(activity);

		log.setPerformedBy(user);

		log.setActivityTime(LocalDateTime.now());

		repository.save(log);
	}

	public List<ActivityLog> latestLogs() {

		return repository.findTop20ByOrderByActivityTimeDesc();
	}
}