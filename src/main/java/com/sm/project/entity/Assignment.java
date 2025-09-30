package com.sm.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subject;
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Status status; // active | overdue | completed

    private int submissions;
    private int totalStudents;

    private String description;
    private Integer points;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public int getSubmissions() { return submissions; }
    public void setSubmissions(int submissions) { this.submissions = submissions; }

    public int getTotalStudents() { return totalStudents; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum Status {
        ACTIVE,
        OVERDUE,
        COMPLETED
    }
}
