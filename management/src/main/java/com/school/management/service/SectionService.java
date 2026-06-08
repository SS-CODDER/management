package com.school.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Section;
import com.school.management.repository.SectionRepository;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    public List<Section> getAllSections() {
        return repository.findAll();
    }

    public void save(Section section){
        repository.save(section);
    }

    public Section getById(Long sectionId) {

        return repository
                .findById(sectionId)
                .orElse(null);
    }

	public boolean existsBySectionName(String sectionName) {
		// TODO Auto-generated method stub
		 return (Boolean) null;
               
	}
}
