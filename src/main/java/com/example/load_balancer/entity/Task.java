package com.example.load_balancer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskType;

    // Large text field for storing JSON or big strings
    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING; // Default status when created

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "assigned_worker")
    private String assignedWorker;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(name = "error_message")
    private String errorMessage;

    public Task() {

    }

    public Task(String taskType, String payload) {
        this.taskType = taskType;
        this.payload = payload;
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void markAsStarted(String assignedWorker) {
        this.startedAt = LocalDateTime.now();
        this.assignedWorker = assignedWorker;
        this.status = TaskStatus.IN_PROGRESS;
    }

    public void markAsCompleted(String result) {
        this.completedAt = LocalDateTime.now();
        this.result = result;
        this.status = TaskStatus.COMPLETED;
    }

    public void markAsFailed(String errorMessage) {
        this.errorMessage = errorMessage;
        this.completedAt = LocalDateTime.now();
        this.status = TaskStatus.FAILED;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskType='" + taskType + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", assignedWorker='" + assignedWorker + '\'' +
                '}';
    }
}
