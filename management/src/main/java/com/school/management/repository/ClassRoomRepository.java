package com.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.school.management.entity.ClassRoom;

public interface ClassRoomRepository
extends JpaRepository<ClassRoom,Long>{
}