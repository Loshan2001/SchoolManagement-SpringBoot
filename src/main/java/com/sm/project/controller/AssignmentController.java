package com.sm.project.controller;

import com.sm.project.entity.Assignment;
import com.sm.project.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService service;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment) {
        try {
            Assignment saved = service.createAssignment(assignment);
            return ResponseEntity.ok("✅ Assignment created successfully! ID: " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Failed to create assignment: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllAssignments() {
        List<Assignment> assignments = service.getAllAssignments();
        if (assignments.isEmpty()) {
            return ResponseEntity.ok("⚠️ No assignments found");
        }
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAssignmentById(@PathVariable Long id) {
        return service.getAssignmentById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)  // force it to ResponseEntity<?>
                .orElseGet(() -> ResponseEntity.badRequest()
                        .body("❌ Assignment not found with ID: " + id));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignment) {
        try {
            Assignment updated = service.updateAssignment(id, assignment);
            return ResponseEntity.ok("✅ Assignment updated successfully! ID: " + updated.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Failed to update: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        try {
            service.deleteAssignment(id);
            return ResponseEntity.ok("✅ Assignment deleted successfully! ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Failed to delete assignment: " + e.getMessage());
        }
    }
}
