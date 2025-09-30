package com.sm.project.service;

import com.sm.project.dao.ExamRepository;
import com.sm.project.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository repository;

    public Exam createExam(Exam exam) {
        return repository.save(exam);
    }

    public List<Exam> getAllExams() {
        return repository.findAll();
    }

    public Exam getExamById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    public Exam updateExam(Long id, Exam examDetails) {
        Exam exam = getExamById(id);
        exam.setTitle(examDetails.getTitle());
        exam.setSubject(examDetails.getSubject());
        exam.setExamDate(examDetails.getExamDate());
        exam.setDurationMinutes(examDetails.getDurationMinutes());
        return repository.save(exam);
    }

    public void deleteExam(Long id) {
        repository.deleteById(id);
    }
}
