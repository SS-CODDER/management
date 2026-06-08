package com.school.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	Optional<Notice> findTopByOrderByPublishDateDesc();
}