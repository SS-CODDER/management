package com.school.management.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.entity.Timetable;
import com.school.management.repository.TimetableRepository;

@Service
public class TimetableService {

	@Autowired
    private final TimetableRepository repository;

    public TimetableService(
            TimetableRepository repository
    ) {
        this.repository = repository;
    }

    public void save(Timetable timetable){

        repository.save(timetable);
    }

    public List<Timetable> getClassTimetable(
            String className
    ){
        return repository.findByClassName(className);
    }

    public List<Timetable> getTeacherTimetable(
            String teacherName
    ){
        return repository.findByTeacherName(teacherName);
    }

    public List<Timetable> getAll(){
        return repository.findAll();
    }
    
    public List<Timetable> getByClassAndSection(
            String className,
            String section) {

        return repository
                .findByClassNameAndSection(
                        className,
                        section
                );
    }
}