package com.sm.project.controller;

import com.sm.project.entity.Course;
import com.sm.project.entity.User;
import com.sm.project.service.CourseService;
import com.sm.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Course> getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{courseId}/assign-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Course assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
        Course course = courseService.getCourseById(courseId).orElseThrow();
        User teacher = userService.getById(teacherId);
        return courseService.assignTeacher(course, teacher);
    }
}
