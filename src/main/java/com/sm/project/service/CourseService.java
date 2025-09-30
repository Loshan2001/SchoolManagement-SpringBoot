package com.sm.project.service;

import com.sm.project.dao.CourseRepository;
import com.sm.project.entity.Course;
import com.sm.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    public Course createCourse(Course course) {
        return repository.save(course);
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return repository.findById(id);
    }

    public Course updateCourse(Course course) {
        return repository.save(course);
    }

    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }

    public Course assignTeacher(Course course, User teacher) {
        course.getTeachers().add(teacher);
        return repository.save(course);
    }

    public Course enrollStudent(Course course, User student) {
        course.getStudents().add(student);
        return repository.save(course);
    }

    public Set<User> getEnrolledStudents(Long courseId) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return course.getStudents();
    }
}
