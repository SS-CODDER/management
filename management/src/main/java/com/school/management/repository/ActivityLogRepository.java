package com.school.management.repository;

import com.school.management.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository
        extends JpaRepository<ActivityLog,Long> {

    List<ActivityLog>
    findTop20ByOrderByActivityTimeDesc();

}