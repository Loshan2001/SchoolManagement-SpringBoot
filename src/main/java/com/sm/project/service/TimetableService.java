package com.sm.project.service;

import com.sm.project.dao.TimetableRepository;
import com.sm.project.entity.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository repository;

    public TimeTable createTimetable(TimeTable timetable) {
        // check overlap
        List<TimeTable> overlaps = repository.findByTeacherNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                timetable.getTeacherName(), timetable.getEndTime(), timetable.getStartTime()
        );

        if (!overlaps.isEmpty()) {
            throw new RuntimeException(" Timetable conflict: Teacher already has a class during this time.");
        }
        return repository.save(timetable);
    }

    public List<TimeTable> getAll() {
        return repository.findAll();
    }

    public TimeTable getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(" Timetable not found"));
    }

    public TimeTable updateTimetable(Long id, TimeTable timetable) {
        TimeTable existing = getById(id);
        existing.setCourseName(timetable.getCourseName());
        existing.setTeacherName(timetable.getTeacherName());
        existing.setStartTime(timetable.getStartTime());
        existing.setEndTime(timetable.getEndTime());
        return createTimetable(existing); // validate overlaps again
    }

    public void deleteTimetable(Long id) {
        repository.deleteById(id);
    }
}
