package com.sm.project.controller;

import com.sm.project.entity.Exam;
import com.sm.project.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService service;

    //  Anyone (student/teacher/admin) can view all exams
    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(service.getAllExams());
    }

    //  Anyone can view single exam
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getExamById(id));
    }

    //  Only TEACHER or ADMIN can create exam
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        return ResponseEntity.ok(service.createExam(exam));
    }

    //  Only TEACHER or ADMIN can update exam
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<Exam> updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        return ResponseEntity.ok(service.updateExam(id, exam));
    }

    //  Only TEACHER or ADMIN can delete exam
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<String> deleteExam(@PathVariable Long id) {
        service.deleteExam(id);
        return ResponseEntity.ok("Exam deleted successfully");
    }
}
