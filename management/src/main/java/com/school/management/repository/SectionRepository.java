package com.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.ClassRoom;
import com.school.management.entity.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	List<Section> findByClassRoom(ClassRoom classRoom);

	boolean existsBySectionName(String sectionName);

}