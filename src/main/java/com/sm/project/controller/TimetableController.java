package com.sm.project.controller;

import com.sm.project.entity.TimeTable;
import com.sm.project.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetables")
public class TimetableController {

    @Autowired
    private TimetableService service;

    // Create timetable (Admin/Teacher only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<?> create(@RequestBody TimeTable timetable) {
        try {
            return ResponseEntity.ok(service.createTimetable(timetable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all timetables
    @GetMapping
    public List<TimeTable> getAll() {
        return service.getAll();
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body( e.getMessage());
        }
    }

    // Update timetable
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TimeTable timetable) {
        try {
            return ResponseEntity.ok(service.updateTimetable(id, timetable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body( e.getMessage());
        }
    }

    // Delete timetable
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteTimetable(id);
            return ResponseEntity.ok("Timetable deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
