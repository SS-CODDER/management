package com.school.management.repository;


import com.school.management.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository
        extends JpaRepository<Holiday,Long> {

    List<Holiday>
    findTop10ByOrderByHolidayDateAsc();
}