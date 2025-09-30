package com.sm.project.controller;

import com.sm.project.entity.Course;
import com.sm.project.entity.User;
import com.sm.project.service.CourseService;
import com.sm.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    private Map<String, Object> buildResponse(String status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        return response;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            Course saved = courseService.createCourse(course);
            return ResponseEntity.ok(buildResponse("success", "✅ Course created successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "❌ Failed to create course", null));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.ok(buildResponse("success", "No courses found", courses));
        }
        return ResponseEntity.ok(buildResponse("success", "✅ Courses retrieved successfully", courses));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(course -> ResponseEntity.ok(buildResponse("success", "✅ Course found", course)))
                .orElseGet(() -> ResponseEntity.badRequest().body(buildResponse("error", "❌ Course not found with ID: " + id, null)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            course.setId(id);
            Course updated = courseService.updateCourse(course);
            return ResponseEntity.ok(buildResponse("success", "✅ Course updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "❌ Failed to update course", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(buildResponse("success", "✅ Course deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "❌ Failed to delete course", null));
        }
    }

    @PostMapping("/{courseId}/assign-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        try {
            Course course = courseService.getCourseById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
            User teacher = userService.getById(teacherId);
            Course updated = courseService.assignTeacher(course, teacher);
            return ResponseEntity.ok(buildResponse("success", "✅ Teacher assigned successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", "❌ Failed to assign teacher: " + e.getMessage(), null));
        }
    }
}
