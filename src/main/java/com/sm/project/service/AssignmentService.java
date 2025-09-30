package com.sm.project.service;

import com.sm.project.dao.AssignmentRepository;
import com.sm.project.entity.Assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository repository;

    public Assignment createAssignment(Assignment assignment) {
        return repository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    public Optional<Assignment> getAssignmentById(Long id) {
        return repository.findById(id);
    }

    public Assignment updateAssignment(Long id, Assignment assignmentDetails) {
        Assignment assignment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setTitle(assignmentDetails.getTitle());
        assignment.setSubject(assignmentDetails.getSubject());
        assignment.setDueDate(assignmentDetails.getDueDate());
        assignment.setStatus(assignmentDetails.getStatus());
        assignment.setSubmissions(assignmentDetails.getSubmissions());
        assignment.setTotalStudents(assignmentDetails.getTotalStudents());
        assignment.setDescription(assignmentDetails.getDescription());
        assignment.setPoints(assignmentDetails.getPoints());

        return repository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        repository.deleteById(id);
    }
}
