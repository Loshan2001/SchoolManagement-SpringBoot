package com.sm.project.dao;

import com.sm.project.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<TimeTable, Long> {

    // Find overlapping timetable entries
    List<TimeTable> findByTeacherNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            String teacherName, LocalDateTime endTime, LocalDateTime startTime
    );
}
